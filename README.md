# FrontController

해당 저장소는 스프링 MVC 패턴 도입 전의 FrontController 패턴을 
도입하면서 점진적으로 발전하는 코드를 작성함으로써 MVC 패턴의 탄생 배경과
장단점을 비교하고 학습하기 위한 저장소 입니다.


### Servlet+JSP만 이용했을 때 ###
```java
@WebServlet("/servlet-mvc/members/save")  
public class MvcMemberSaveServlet extends HttpServlet {  
    private MemberRepository repository = MemberRepository.getInstance();  
    @Override  
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        String userName = request.getParameter("username");  
        int age = Integer.parseInt(request.getParameter("age"));  
  
        Member member = new Member(userName, age);  
        repository.save(member);  
  
  
        request.setAttribute("member",member);  
        String viewPath = "/WEB-INF/views/save-result.jsp";  
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPath);  
        requestDispatcher.forward(request, response);  
    }  
}
```
```java
@WebServlet("/servlet-mvc/members")  
public class MvcMemberListServlet extends HttpServlet {  
    private MemberRepository repository = MemberRepository.getInstance();  
    @Override  
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        List<Member> allMemberList = repository.findAll();  
        request.setAttribute("allMembers", allMemberList);  
  
        String viewPath = "/WEB-INF/views/members.jsp";  
        request.getRequestDispatcher(viewPath)  
                .forward(request, response);  
  
    }  
}
```
```java
@WebServlet("/servlet-mvc/members/new-form")  
public class MvcMemberFormServlet extends HttpServlet {  
    @Override  
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        String viewPath = "/WEB-INF/views/new-form.jsp";  
        RequestDispatcher path = request.getRequestDispatcher(viewPath);  
        path.forward(request, response);  
    }  
}
```
서블렛과 JSP만을 이용했을 경우, 각 요청에 대해서 URL 매핑정보를 새겨줘야하고, View로 넘어가는 중복로직과 HttpServlet 객체가 중복으로 적용됨.

그리고 이렇게 HttpServlet에 의존하게 되면 테스트 코드 작성 등 개발 효율성이 저하되며, "로직이 중복된다" 라는 이유만으로도 충분히 개선의 여지가 존재한다.

## ***Version1*** ##
### Version1 ###

![[Pasted image 20240220150526.png]]
<프론트 컨트롤러 Version1의 기본 구조>

Servlet과 비슷한 모양의 컨트롤러 인터페이스를 도입함으로써, 각 컨트롤러들은 해당 인터페이스를 구현하면 된다.

이렇게 하면 요청 시 해당 인터페이스를 호출함으로써 구현과 관계없이 로직의 일관성을 가져갈 수 있다.

```java
public interface ControllerV1 {  
    void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;  
}
```

```java
 public class MemberSaveControllerV1 implements ControllerV1{  
    private MemberRepository repository = MemberRepository.getInstance();  
  
    @Override  
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        String userName = request.getParameter("username");  
        int age = Integer.parseInt(request.getParameter("age"));  
  
        Member member = new Member(userName, age);  
        repository.save(member);  
        // 호출하고 양식에 맞게 입력한 뒤 정보를 저장  
  
  
        // 그리고 request 객체에 정보를 바인딩 한뒤 jsp View로 넘김 -> detail        request.setAttribute("member",member);  
        String viewPath = "/WEB-INF/views/save-result.jsp";  
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPath);  
        requestDispatcher.forward(request, response);  
    }  
}
```
```java
  
public class MemberListControllerV1 implements ControllerV1{  
    private MemberRepository repository = MemberRepository.getInstance();  
  
    @Override  
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        List<Member> allMemberList = repository.findAll();  
        request.setAttribute("allMembers", allMemberList);  
  
        String viewPath = "/WEB-INF/views/members.jsp";  
        request.getRequestDispatcher(viewPath)  
                .forward(request, response);  
    }  
}
```

```java
// *처리를 함으로써 v1의 하위 웹 매핑 객체들이 작동하게 됨  
public class FrontControllerV1 extends HttpServlet {  
    private  Map<String, ControllerV1> controllerV1Map = new HashMap<>();  
  
    public FrontControllerV1() {  
        controllerV1Map.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());  
        controllerV1Map.put("/front-controller/v1/members/save", new MemberSaveControllerV1());  
        controllerV1Map.put("/front-controller/v1/members/members", new MemberListControllerV1());  
    }  
    @Override  
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        String requestUrl = request.getRequestURI();  
        ControllerV1 controllerV1 = controllerV1Map.get(requestUrl);  
  
        if (controllerV1 == null) {  
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);  
            return;  
  
        }  
        controllerV1.service(request,response);  
    }  
}
```
그리고 이렇게 맨 앞에서 요청 처리를 담당할 프론트컨트롤러가 딱 한번 HttpServlet을 상속받음으로 써 요청이 들어오면 해당 요청에 맞는 객체를 반환하는 메커니즘으로 코드를 중앙 집중화 한다.

결론 : 기존의 Servlet을 이용하여 코드를 작성할 경우에, 각 컨트롤러(요청을 처리할 Servlet)들은 URL 매핑 정보를 가지고 있어야 되며, 모든 컨트롤러들은 HttpServlet을 상속받아야 하는 번거러움이 있었다.

이를 해결하기 위해 컨트롤러들의 앞(Front) 프론트 컨트롤러 클래스에 모든 URL 요청이 본인을 거쳐가게 한다음에 HttpServlet 상속도 딱 한번 받게 함으로써 코드의 중앙집중화가 가능해졌다.

