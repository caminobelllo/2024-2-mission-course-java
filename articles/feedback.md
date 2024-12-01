# 공통 피드백
여러분들의 코드를 한번 쭈욱 살펴봤어요.
공통적으로 한번 고민해보면 좋을 거 같은 키워드를 같이 알아보아요.
(추가로 3주차까지 계속 사용할 코드이기 때문에 리팩토링 해보는 것도 괜찮을거 같네요!)

## 불변성(NOT DONE)
불변성은 코드의 버그를 줄일 뿐만 아니라, 개발자가 코드를 편하게 짜게 도와줘요.  

예시와 함께 알아볼께요.
```java
    public class Character {
        // ...
        private List<Skill> skills;
    }
    
    public class Skill {
        private int min;
        private int max;
        private String name;
        // ...
    }
```


## interface
인터페이스를 객체의 협력 관계를 나타낼 수 있는 좋은 수단이라고 소개했어요.  
간단하게 캐릭터와 스킬 객체 간의 관계로 생각해볼께요.  
간단한 협력관계를 떠올려보면, `캐릭터가 스킬을 사용하여 데미지를 입힌다` 가 있을거에요.  
즉, 스킬은 캐릭터에게 `데미지를 알려줘야하는 책임` 이 부여돼요.  
반대로 캐릭터는 `데미지를 입는다는 책임` 이 있을 거에요.  
객체가 다른 객체로부터 책임을 요구받아 구현을 해야하는 의무가 발생했어요.  

이런 식으로 다른 객체로부터 요청받은 책임들을 나열하는 곳을 `인터페이스`라고 생각하면 좋을 거 같아요.  
반대로, 객체 내부에서의 작업, 예컨데 검증 로직이라든가, 객체의 util 함수의 경우는 적을 필요가 없을거 같아요.

## getter, setter
getter, setter 는 최대한 지양해야 해요.  
만약 둘을 모두 사용한다면, 그 멤버 변수는 `public` 과 무슨 차이점이 있나요?  

객체지향의 중요한 특성 중 `캡슐화` 라는 용어를 들어봤을 거에요.  
캡슐화를 통해 객체의 경계를 분명히 하여, 객체 내부의 구현의 자율성을 보장해요.  
다시 말하면, 객체의 내부 구현은 신경쓰지 않겠다는 의미에요.  
대신 원하는 데이터를 주기만 하면 된다는 의미이죠.  
  
만약 객체(A)에서 다른 객체(B)의 `getter` 를 사용한다고 해볼께요.  
근데, B 에서 A의 getter 를 왜 사용하나요?  
A 의 책임을 B 에서 하고 있진 않나요?  

예를 들어, `main` 에서 `Character` 객체에서 사용가능한 `Skill` 들만 필요하다고 해볼께요.  
이 때, 이 로직을 `main` 이 `getSkills` 를 호출해서 일일이 검증해야 할까요?  
그보다는 `Character` 내부에서 `availableSkills` 라는 메소드를 만드는게 더 좋아보여요.  
이를 통해 불필요한 멤버변수 노출을 막고, 좀 더 직관적인 메소드명이 나오게 되었어요.  
  
`setter` 의 경우도 비슷합니다. 책임을 전가하고 있진 않은지 주의해야해요.  
또한 정말 필요하다면, 그 목적이 분명하도록 메서드명을 지어야해요.  
  
그래서 제가 추천하는 방법은 일단 최대한 `getter`, `setter` 를 쓰지 않으려고 노력하는 것입니다.   
정말 고민해도 필요하다면, 사용할 순 있습니다. 뭐든지 100% 네버는 없으니까요.  
하지만, 고민하다보면 대부분의 경우는 필요하지 않을 거에요.

## abstract class vs interface
사실 저도 최근에 알게 된 문법인데요.   
`abstract class` 가 `interface` 를 대체할 수 있지 않을까? 하는 질문을 어떤 분께 받았어요.  
이에 대해 찾아본 자료와 의견을 공유해보면 좋을거 같아서 글로 남겨보아요.  
간단히 문법적으로 설명하면, `abstract class` 는 공통 멤버 변수를 정의할 수 있어요.  
반면에 `interface` 에서 변수를 정의하면 그것은 상수로서 작동해요.  

간단한 예시를 들어볼께요.
```java
    abstract class Animal {
        private int leg;
        // ...
    }
    
    public class lion extends Animal {
        // leg 멤버 변수가 정의되어 있음
        // ...
    }
    public class tiger extends Animal {
        // leg 멤버 변수가 정의되어 있음    
        // ...
    }
```
하지만 `interface` 는 저러한 기능을 지원하진 않아요.  
일일이 `private int leg` 를 선언해줘야 합니다.  
자세한 이야기는 [abstract vs interface](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4-vs-%EC%B6%94%EC%83%81%ED%81%B4%EB%9E%98%EC%8A%A4-%EC%B0%A8%EC%9D%B4%EC%A0%90-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0#%EC%B6%94%EC%83%81_%ED%81%B4%EB%9E%98%EC%8A%A4_%EC%A0%95%EB%A6%AC)
를 참고하면 좋을거 같아요.  

제 개인적인 감상은 `abstract` 문법은 추후 리팩토링에서, 혹은 구현을 하면서 고려해야 할 사항인 것 같아요.  
전체적인 객체 설계는 `interface` 를 통해서, 구현하면서 중복되는 코드는 `abstract` 를 통해 줄일 수 있을 거 같다고 생각했어요.