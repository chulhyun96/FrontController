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


## ***Version2*** ##
View의 분리

기존 V1의 코드에서 보면 각 컨트롤러들은 아직도 코드의 중복되는 로직을 가지고 있다.
```java
String viewPath = "/WEB-INF/views/new-form.jsp";
  RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
  dispatcher.forward(request, response);
```
바로 View로 forward하는 부분이다.

중복되는 부분이 있다면 개선의 여지가 있다는 뜻이다.
![[Pasted image 20240220154758.png]]
version2의 다이어 그램

이번엔 MyView라는 클래스를 만들어서 요청 시 프론트 컨트롤러를 거친 후 요청에 맞는 컨트롤러를 호출한다. 그 후 View를 반환하여 MyView 클래스의 render()를 호출한다.


```java
public class MyView {  
    String viewPath;  
    public MyView(String viewPath) {  
        this.viewPath = viewPath;  
    }  
    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        request.getRequestDispatcher(viewPath).forward(request, response);  
    }  
}
```

```java
public interface ControllerV2 {  
    MyView service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;  
}
```
각각의 컨트롤러들이 구현할 인터페이스의 반환 타입으로 MyView를 반환하게 해준다.

```java
@WebServlet("/front-controller/v2/*")  
public class FrontControllerV2 extends HttpServlet {  
    private final Map<String, ControllerV2> controllerV2Map = new HashMap<>();  
  
    public FrontControllerV2() {  
        controllerV2Map.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());  
        controllerV2Map.put("/front-controller/v2/members/save", new MemberSaveControllerV2());  
        controllerV2Map.put("/front-controller/v2/members/members", new MemberListControllerV2());  
    }  
  
    @Override  
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        String requestURI = request.getRequestURI();  
        ControllerV2 controllerV2 = controllerV2Map.get(requestURI);  
  
        if (controllerV2 == null) {  
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);  
            return;  
        }  
  
        MyView view = controllerV2.service(request, response);  
        view.render(request, response);  
    }  
}
```
그 후 프론트 컨트롤러에서는 결과를 MyView클래스에서 처리할 수 있도록 view.render 메서드를 호출 함으로써 좀 더 객체지향적인 코드를 가지게 된다.

그럼 요청이 들어올 때, <p style="color: green">해당 url 매핑 정보와 맞는 컨트롤러가 호출 되고, 컨트롤러의 service 로직이 실행 된후 MyView 객체에 View로 보여줄 path를 담아 반환하면 MyView에서 이를 처리하여 forward한다.</p>

결론 : V1 - 컨트롤러 인터페이스 도입 후 Service메서드를 일괄처리 할 수 있게 해주며 HttpServlet도 프론트 컨트롤러에서만 상속받는다. 이러면 각각의 컨트롤러가 url 매핑 정보를 갖고 있지 않아도 되고, HttpServlet을 상속받지 않아도 된다.

그럼 각각의 컨트롤러들은 요청 정보에 맞는 로직을 실행 후 View로 forward 해줘야 하는 로직이 중복되어있었는데, 이를 MyView 클래스에게 책임을 위임함으로써 컨트롤러들은 좀 더 책임에 자유로워 진다.

Model의 추가

##### 서블릿 종속성 제거 #####


컨트롤러 입장에서는 HttpServlet이 꼭 필요할까? 라는 의문이 들게 된다.  이게 무슨 말이냐면 모든 컨트롤러를 담당하는 클래스들은 파라미터의 인자로 HttpServlet 객체를 담고 있다.

```java
public class MemberSaveControllerV2 implements ControllerV2{  
    private MemberRepository repository = MemberRepository.getInstance();  
  
    @Override  
    public MyView service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        String userName = request.getParameter("username");  
        int age = Integer.parseInt(request.getParameter("age"));  
  
        Member member = new Member(userName, age);  
        repository.save(member);  
  
  
        request.setAttribute("member",member);  
        return new MyView("/WEB-INF/views/save-result.jsp");  
  
    }  
}
```
해당 클래스를 보면 컨트롤러 인터페이스를 구현한 상태인데, service메서드의 파라미터 인자로 HttpServlet을 필요로한다.

하지만 해당 클래스가 Save로직을 수행함에 있어서 HttpServlet 객체가 필요한게 아닌, 단순한 파라미터 정보가 필요할 뿐이다.

파라미터 정보가 필요하기에 HttpServlet을 인자로 받은것인데 객체지향적인 관점에서 볼 때 해당 컨트롤러 클래스는 HttpServlet API에 너무 의존하고 있으며, 이는 유연성을 떨어트린다는 것이다.

그리고 해당 클래스는 파라미터의 정보를 받기 위해, Request만 사용하고 있다. 불필요한 Response에도 의존하고 있다는 것이다.

이를 해결 하기 위해서는 요청 파라미터 정보는 자바의 Map으로 대신 넘기도록 하면 지금 구조에서는 컨트롤러가 **서블릿 기술을 몰라도 동작할 수 있다.

그리고 request 객체를 Model로 (기존 MVC 패턴에서의 request.setAttribute)사용하는 대신에 별도의 Model 객체를 만들어서 반환하면 된다.


### ***Version3*** ###

View의 이름 제거

기존 Version2에서의 방식을 보면 컨트롤러 인터페이스를 구현한 구현체인 컨트롤러 클래스들은 모두 MyView 객체를 생성해서 인자로 경로를 주입하여 반환하였다.

이 부분에서 또한 WEB-INF/views .... 부분이 중복이 되는데 이 부분 또한 프론트 컨트롤러에서 처리하도록 단순화 시킨 뒤 컨트롤러 구현체들은 뷰의 논리 이름만을 반환하도록 만들어준다.

뷰의 중복 이름을 프론트 컨트롤러에서 하게 두면 나중에 뷰의 폴더 위치가 변하게 되어도, 프론트 컨트롤러만 고치면 되는 이점이 있다. -> 이는 유지보수성을 향상시킨다.

![[Pasted image 20240220163511.png]]
version2와의 차이점은 v2에서는 컨트롤러 호출 후 MyView를 반환해줬었다. V3에서는 modelView를 반환하면서 논리이름을 반환하는데 ViewResolver에서 view의 물리이름를 반환한다.

ModelView 클래스
```java
@Setter  
@Getter  
public class ModelView {  
    private String viewName; // view의 논리이름을 가져갈 속성  
    private Map<String, Object> model = new HashMap<>(); // HttpServlet의 setAttribute 메서드를 대체, 의존성을 제거하기위한 속성  
  
    public ModelView(String viewName) {  
        this.viewName = viewName;  
    }  
  
}
```
생성자메서드로 view의 논리이름을 받아준다.

```java
import Servlet.HelloSpring.domain.web.frontcontroller.ModelView;  
  
import java.util.Map;
public interface ControllerV3 {  
    //Map<String, String> paraMap 해당 코드조각이  
    // 더이상 servlet의 getParameter() 메서드를 의존하지 않음.(사용하지 않음)  
    ModelView process(Map<String, String> paraMap);  
}
```
그리고 컨트롤러 인터페이스에서는 request.getParam()을 대체할 Map을 인자로 받아준다.

```java
public class MemberFormControllerV3 implements ControllerV3{  
  @Override  
    public ModelView process(Map<String, String> paraMap) {  
        return new ModelView("new-form");  
    }  
}

public class MemberListControllerV3 implements ControllerV3 {  
    private MemberRepository repository = MemberRepository.getInstance();  
    @Override  
    public ModelView process(Map<String, String> paraMap) {  
        List<Member> allMemberList = repository.findAll();  
        ModelView modelView = new ModelView("members");  
        modelView.getModel().put("allMembers", allMemberList);  
        return modelView;  
    }  
}

public class MemberSaveControllerV3 implements ControllerV3 {  
    private final MemberRepository repository = MemberRepository.getInstance();  
  
    @Override  
    public ModelView process(Map<String, String> paraMap) {  
        String username = paraMap.get("username");  
        int age = Integer.parseInt(paraMap.get("age"));  
  
        Member member = new Member(username,age);  
        repository.save(member);  
  
        ModelView modelView = new ModelView("save-result");  
        modelView.getModel().put("member",member);  
        return modelView;  
    }
```
각각 요청에 맞는 로직을 작성해주는데, 가장 큰 차이점은 requset.setAttribute() 메서드와 getRequestDispatcher("viewName") 메서드를 ModelView.getModel이, viewName은 ModelView의 생성자가 대체함으로써 더이상 servlet에 종속적이지 않다는 것이다.

modelView.getModel()메서드는 Map<String, Object> 를 반환함으로써 request.setAttribute와 완벽히 호환된다고 볼 수 있다.

해당 정보를 FrontController로  반환해준다.
```java
publc class FrontControllerV3 extends HttpServlet
   HashMap<String, String> paraMap = createParaMap(request);  
    ModelView mv = controllerV3.process(paraMap);  
  
    String viewName = mv.getViewName(); 
    MyView view = viewResolver(viewName);  
  
    view.render(mv.getModel(), request, response);  
}  
  
private MyView viewResolver(String viewName) {  
    return new MyView("/WEB-INF/views/" + viewName + ".jsp");  
}  
  
private HashMap<String, String> createParaMap(HttpServletRequest request) {  
    // 웹에서 파라미터로 넘어온 정보를 모두 조회해준뒤  HashMap에 저장함 -> 그 뒤 process 메서드의 파라미터로 넘김.  
    HashMap<String, String> paraMap = new HashMap<>();  
    //Request 객체로 받은 모든 요청 파라미터 정보를 조회함  
    //예를 들어, 요청 URL이 ?username=john&age=30일 경우,  
    //paraMap은 {"username": "john", "age": "30"}과 같은 형태로 채워집니다.  
    request.getParameterNames().asIterator()  
            .forEachRemaining(paraInfo -> paraMap.put(paraInfo, request.getParameter(paraInfo)));  
  
    return paraMap;  
}
```
saveController의 process 메서드가 실행 되기전에, request.getParameterNames 메서드를 이용하여, 쿼리 파라미터로 들어온 모든 정보를 순회하여 Map<String,String>에 담아둔뒤에 Map을 반환하고, Process 메서드의 인자로 넘겨준다.


```java
  public ModelView process(Map<String, String> paraMap) {  
        String username = paraMap.get("username");  
        int age = Integer.parseInt(paraMap.get("age"));  
		Member member = new Member(username,age);  
```
그럼 해당 로직에서 넘어온 이름을 가지고 값을 꺼내와  Member객체가 생성되는 메커니즘이다.

그 뒤 process가 반환하는 ModelView는 논리이름과 (request.getRequestDispatcher에 들어갈 View의 ClassPath)와 setAttribute와 완전히 호환되는 정보를 가진 상태이다.

그리고 나서 ModelView의 getViewName 메서드를 호출하여 view의 논리이름만을 가지고 와서 MyView 클래스에다가 항상 동적일 수 있게 인자로 viewName값을 넣어준다.
```java
private MyView viewResolver(String viewName) {  
    return new MyView("/WEB-INF/views/" + viewName + ".jsp");  
}  
```

넣어주고 난 뒤에 forwarding을 해줘야 하는데 해당 역할을 render() 메서드가 수행한다

```java
view.render(mv.getModel(), request, response); 

public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    model.forEach((key, value) -> request.setAttribute(key, value));  
    request.getRequestDispatcher(viewPath).forward(request, response);  
}
```
render 메서드의 파라미터로 ModelView클래스의 Map에 담은 객체 정보를 받기 위해서 인자를 추가해 준 뒤,
Map에 담긴 객체 정보를 꺼내서, request.setAttribute에 담아준다. 그 뒤 forwarding 하는 구조이다.


V2, V3의 save 로직 비교
![[Pasted image 20240220165523.png]]
servlet의 종속성을 제거하고, Map으로 이를 대체하였다. 그 뒤 servlet의 종속성을 제거 하였으므로 setAttribute메서드를 사용할 수 없으니, ModelView 클래스가 이를 대체하였다.

결론 : 좀 더 코드가 복잡해지고, 클래스가 늘어났다. 이는 HttpServlet의 종속성을 제거하기 위해서 여러 단계로 나눴기 때문이다.  
HttpServlet을 의존하고 있는 클래스는 FrontController와 MyView의 render 메서드 뿐이다. 허나 아직도 여전히 코드의 중복은 보인다. (내가 보기에는 종속성을 제거 하고 유연하게 하기 위해 쪼개고 쪼갠 것 뿐. 결국 setAttribute에 해당하는 model.getModel() 메서드는 중복될 것이다.)

