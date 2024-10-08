
INSERT INTO
    `USER` (id, username, password, simple_description, profile_image, login_type, jwt_validator, create_date, update_date, delete_date)
VALUES
    (1,'admin', '$2a$12$/w0QNI2aBuSBzyQS.dKQDefH8b2u2JF8e7Jgd.wySld3H8DE8WKzC', null, 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png', 'DEFAULT', 0, CURRENT_TIMESTAMP, NULL, NULL),
    (2, 'temp1', '$2a$12$HCVYl0wt61JgzeezktCu4eEQN2sj1Q35iexD9Auhuo75NFDePRhWi', null, 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png', 'DEFAULT', 0, CURRENT_TIMESTAMP, NULL, NULL),
    (3, 'temp2', '$2a$12$HCVYl0wt61JgzeezktCu4eEQN2sj1Q35iexD9Auhuo75NFDePRhWi', null, 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png', 'DEFAULT', 0, CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO
    USER_ROLE (user_id, role, create_date)
VALUES
    (1, 'ADMIN', CURRENT_TIMESTAMP),
    (1, 'USER', CURRENT_TIMESTAMP),
    (2, 'USER', CURRENT_TIMESTAMP),
    (3, 'USER', CURRENT_TIMESTAMP);

INSERT INTO
    ARTICLE (id, user_id, title, thumbnail, content, create_date, update_date, delete_date)
VALUES
    (1, 2, '(번역) 가장 빠른 자바스크립트 프레임워크', 'https://velog.velcdn.com/images/sehyunny/post/e48644e2-add4-44da-905c-b36a278d9d8f/image.png', '![image](https://velog.velcdn.com/images/sehyunny/post/e48644e2-add4-44da-905c-b36a278d9d8f/image.png)

# 세계 최초의 O(1) 자바스크립트 프레임워크 살펴보기

우리는 지금부터 "Qwik" 에 대해 이야기하려고 합니다. Qwik은 크기와 복잡성에 상관없이 즉시 로딩되는 애플리케이션을 제공하고, 규모에 맞게 일관된 성능을 달성할 수 있는 새로운 종류의 웹 프레임워크입니다.

Qwik은 2년간의 개발 기간을 거쳐 현재 베타 단계이며, 완전한 기능, 안정된 API, 블로킹 이슈 제거, 그리고 충분한 문서와 함께 프로덕션에 나갈 준비가 되었습니다. 이제 이 프레임워크가 무엇인지를 살펴보겠습니다.

문제를 해결하기 위한 목적으로 많은 자바스크립트 프레임워크가 존재해왔으며 이들 중 대부분은 비슷한 문제를 해결하려고 합니다. 그러나 Qwik은 다른 프레임워크가 풀지 못한 문제를 해결하려고 합니다. 그 문제에 대해 먼저 알아봅시다.', CURRENT_TIMESTAMP, NULL, NULL),
    (2, 2, 'React Rendering Optimization', 'https://velog.velcdn.com/images/hyunjine/post/16b2aa12-7e9e-49ea-b759-0544fbcfc626/image.png', '![image](https://velog.velcdn.com/images/hyunjine/post/16b2aa12-7e9e-49ea-b759-0544fbcfc626/image.png)

React에서 함수나 값을 최적화하기 위해 useMemo, useCallback을 활용해보신 경험이 있으실 것입니다. 이 글에서는 useMemo와 useCallback이 React에 존재하는 이유와 이를 사용해야하는 상황에 대하여 알아보고 대다수의 경우에 useMemo와 useCallback을 제거할 수 있는 이유에 대해 설명해보려고 합니다.

# useCallback
모든 값이나 함수에 useMemo와 useCallback을 사용하는것이 성능을 높여줄 수 있을까요?

성능은 수치에 기반해야합니다. 예제를 통해 직접 확인해봅시다.

``````
function Toggle() {
  const initialPerson = ["현진", "찬희", "시온", "하령", "영기", "그루트"];
  const [person, setPerson] = useState(initialPerson);

  const hide = (person: string) =>
    setPerson((allPerson) => allPerson.filter((p) => p !== person));

  return (
    <div>
      <h1>Toggle Person</h1>
      <div>
        {person.length === 0 ? (
          <button onClick={() => setPerson(initialPerson)}>show</button>
        ) : (
          <ul>
            {person.map((p) => (
              <li key={p}>
                <button onClick={() => hide(p)}>click</button> {p}
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
}
``````', CURRENT_TIMESTAMP, NULL, NULL),
    (3, 2, 'Next.js 13 정리', 'https://velog.velcdn.com/images/yoosion030/post/2544cc56-49bf-4df7-87c7-013b7d998d73/image.jpeg', '![image](https://velog.velcdn.com/images/yoosion030/post/2544cc56-49bf-4df7-87c7-013b7d998d73/image.jpeg)

2022년 10월 26일 수요일, Next.js Conf에서 Next.js 13을 발표했습니다. Next.js 12도 이제 막 공부하고 있는데 벌써 13이 나오다니... 아직 베타 버전이 많아 수정될 부분도 있을 것 같지만 늦기 전에 미리미리 공부하는 게 낫겠죠?

아무튼 Next.js 공식문서를 보면서 Next.js 13에서 추가된 점을 정리해보았습니다. 잘못된 내용이 있다면 피드백 부탁드립니다.

# 한 줄 정리
Next.js Conf에서 발표한 바와 같이 Next.js 13은 제한 없이 동적으로 작동할 수 있는 토대를 마련합니다.', CURRENT_TIMESTAMP, NULL, NULL),
    (4, 2, '우테코 회고', null, '예상보다 조금 빠르게 우테코를 떠나게 됐다. 우테코 과정을 되돌아보고 잘했던 것과 못했던 것들을 짚어보면 새로운 시작에도 도움이 될 것 같다.

딱 작년 이맘때 쯤 우테코에 지원을 했고 우테코에 합격하고 나서도 갈지 말지 되게 고민을 많이 했다. 취업을 목적으로 하던 공부 탓에 개발에 흥미가 많이 떨어졌기 때문이었다. 우테코도 하다가 재미 없거나 안맞으면 나가야지라는 생각으로 들어왔는데 결국 수료까지 하고 나간다. 진짜 좋은 사람들을 만나 멋진 일들이 많이 일어났다.

# 페어 프로그래밍
우테코에 와서 처음 경험해봤던 것 중 하나가 페어 프로그래밍이었다. 하나의 프로그램을 두명이상이서 만드는 것이 처음에는 너무 비효율적이라 생각했지만, 혼자서 프로그래밍을 할 때보다 훨씬 많이 배웠다. 사소하게는 툴이나 단축키부터 크게는 올바른 코드가 뭔지, 천만명이 넘게 사용하는 서비스의 코드라면 어떻게 해야할지에 대해서도 이야기를 나눴다.

에덴, 승팡, 차리, 쿼리치, 후디, 루키, 오찌, 아키, 알파, 더즈, 이스트, 크리스, 헌치, 조시 등 한명 한명이 배울게 많은 크루들이었다. 정말 각각 크루들에게서 다양하게 배울 점들이 있고, 좋아 보이는 부분들은 따라하려고 노력을 해봤다.

페어프로그래밍을 하다보면 다른 사람은 어떻게 생각하는지, 나의 의견을 주장할 때는 어떻게 해야하는지에 대해서 많이 생각해볼 수 있는 기회들이 생겼다. 이런 경험들은 현업에 가서도 중요하게 사용될 것 같다.

# 근로
부산이 본가인 덕분에 근로를 할 수 있었고, 덕분에 학습에 보다 집중하면서 편하게 공부할 수 있었다. 처음에는 제작 근로가 되지 못한게 못내 아쉬웠다. 난생 처음해보는 카드 뉴스를 만들어야 했기 때문이다. 하지만 브랜딩 근로를 하면서 정말 중요한 걸 배웠다.

처음에는 카드 뉴스를 만드는 것이 사소한 일이라 생각했고, 그리 정성 들이지 않았다. 하지만 그런 태도를 갖고 맡은 일을 하니 금방 티가 났다. 조금만 신경 썼으면 고칠 수 있었던 것들을 고치지 않은 채 결과물이라고 갖고 가서 피드백을 받았을 때 많이 부끄러웠다. 크루들 중에는 같은 일을 맡더라도 꼼꼼하게 확인하고 퀄리티를 높여서 가져오는 크루들이 있었는데, 내가 관리자라면 그런 친구들이랑 일하고 싶겠다라는 생각을 했다.', CURRENT_TIMESTAMP, NULL, NULL),
    (5, 3, '질문 잘하는 법', 'https://velog.velcdn.com/images/hamham/post/105cc93b-03cf-4cc0-8f1b-6f1768e66e20/image.PNG', '# 개요
우아한테크코스 5기 프리코스 슬랙에서 코치님이 질문 잘하는 법에 대한 글과 유튜브 URL을 올려주셨다.

![image](https://velog.velcdn.com/images/hamham/post/105cc93b-03cf-4cc0-8f1b-6f1768e66e20/image.PNG)

좋은 개발자의 역량이라고 한다면 코드를 잘 짜는 것? 소프트 스킬? 등만 생각했지 질문을 잘하는 것을 좋은 개발자의 역량이라고 생각해본 적은 없었다.

그래서 과연 코치님이 언급하신 ''''질문을 잘하는'''' 법이 어떤 것인지 궁금해지기 시작했고, 첨부된 하나의 영상과 다른 하나의 글을 보고 정리해보기로 했다.', CURRENT_TIMESTAMP, NULL, NULL),
    (6, 3, '나의 첫 스타트업 후기', 'https://velog.velcdn.com/images/maketheworldwise/post/2250e842-d29b-442b-90aa-3398e2ec4d78/image.gif', '![image](https://velog.velcdn.com/images/maketheworldwise/post/2250e842-d29b-442b-90aa-3398e2ec4d78/image.gif)

# 이 글의 목적?
최근에 이력서와 포트폴리오를 쓰느라 블로그 운영에 신경을 못썼다. 비록 같은 분야는 아니지만 그 동안 해왔던 내용들을 모두 정리하려다보니 어느새 너무 시간이 많이 지나버렸다. 그래서 다시 블로그를 활성화시킬겸 (시간이 많이 지났지만) 스타트업을 다녔던 회고를 작성해보고자한다. (이전에 개발자로서의 회의감에 대해 글을 올린적 있어 중복되는 내용이 있겠지만... 😂)

# 왜 스타트업인가?
내가 즐겨보았던 드라마중 하나는 바로 ''''스타트업''''이다. 회사와 성장하는 이야기를 그린 드라마이고 그 과정에서 발생하는 여러 문제들을 해결해나가는 부분이 낭만있다고 생각했다. 또한 드라마를 보면서 한번도 들어보지 못한 생소한 단어들이 등장하는데, 개발 용어만 공부하던 나에게 있어서 설렘을 부여해줬다. (수지짱...👍)', CURRENT_TIMESTAMP, NULL, NULL),
    (7, 3, '최신 Chrome 브라우저 렌더링 방식을 알아보자!', 'https://velog.velcdn.com/images/himprover/post/3a33709a-0048-49f3-8c99-bc8676ebb458/image.png', '![image](https://velog.velcdn.com/images/himprover/post/3a33709a-0048-49f3-8c99-bc8676ebb458/image.png)

# 들어가며
## 개요

![image](https://media.giphy.com/media/l2JehgpneMlOjaewE/giphy.gif)

> 브라우저 렌더링 방식에 대해 설명해보시겠어요?

> 브라우저에 google.com을 검색하면 어떻게 되는지 설명해주세요.

프론트엔드 면접 질문 하면 빠지지 않는 단골 주제는 바로 브라우저의 렌더링 방식 이라고 할 수 있다.
아마 이 글을 보게된 사람들도 면접을 준비하며 온 경우가 많을 것 같다.

이걸 준비하다 보면 이런 생각이 들게 된다.', CURRENT_TIMESTAMP, NULL, NULL),
    (8, 3, 'veltrends 개발 후기', 'https://velog.velcdn.com/images/velopert/post/043d71d9-5a66-4795-b960-ba7ff9384947/image.png', '![image](https://velog.velcdn.com/images/velopert/post/043d71d9-5a66-4795-b960-ba7ff9384947/image.png)

Veltrends는 개발, IT, 디자인, 스타트업 관련 유익하고 재미있는 소식들을 한 눈에 볼 수 있는 서비스입니다.

이 포스트에서는 이 프로젝트를 만들면서 했던 생각들을 여러분들께 공유해보고자 합니다.

# 프로젝트를 만든 이유
이 프로젝트를 시작한 주요 목적은 기존에 제가 집필했던 『리액트를 다루는 기술』, 『리액트 네이티브를 다루는 기술』 에서 다루는 마지막 프로젝트 개발 튜토리얼을 대체하는 것이였습니다. 그래서 최대한 복잡하지 않고 이해하기 쉬운 코드로 작성을 해야겠다는 생각을 가지고 시작을 했습니다. 하지만 만들다보니, 이왕 만드는거 조금 더 진심으로 만들어보자라는 마음을 갖게 됐고, 서비스를 충분히 활성화 시킬 수 있지 않을까 싶어서 프로덕션 배포까지 진행을 하게 되었습니다.

열심히 개발을 하다 보니 프로젝트의 결국 좀 복잡해지긴 했고.. 입문자가 그대로 따라 할 수 있는 수준을 조금 벗어나게 되었습니다. 나중에 책에 실은 프로젝트의 경우엔 Veltrends Lite 버전을 만들 계획입니다.

이 프로젝트는 해외의 Hackernews와 국내의 GeekNews를 모티브로 만들어졌습니다.

이 프로젝트는 최대한 이미 알고 있는 기술을 사용해서 빠르고 쉽게 만드는게 의도였지만 프로젝트 개발을 하다보니 이번 프로젝트를 통해 새로운 것들을 꽤나 많이 경험하게 되었습니다. 중요하다고 생각하는것들 순으로 정리를 해보겠습니다.
', CURRENT_TIMESTAMP, NULL, NULL),
    (9, 3, '[번역] Node.js 개요: 아키텍처, API, 이벤트 루프, 동시성', 'https://velog.velcdn.com/images/cookie004/post/2f15a09c-ab91-4964-a385-96eb1ec9eeda/image.png', '![image](https://velog.velcdn.com/images/cookie004/post/2f15a09c-ab91-4964-a385-96eb1ec9eeda/image.png)

4.1  Node.js 플랫폼

4.1.1 Node.js 전역 변수
4.1.2 Node.js 내장 모듈
4.1.3 Node.js 함수의 다양한 스타일
4.2 Node.js 이벤트 루프

4.2.1 실행을 완료하면 코드가 더 간단해집니다
4.2.2 Node.js 코드가 싱글 스레드에서 실행되는 이유가 뭘까요?
4.2.3 실제 이벤트 루프에는 여러 단계가 있습니다
4.2.4 Next-tick 태스크와 마이크로 태스크
4.2.5 태스크를 직접 스케줄링하는 다양한 방법 비교
4.2.6 Node.js 앱은 언제 종료되나요?
4.3 libuv: Node.js용 비동기 I/O 등을 처리하는 크로스 플랫폼 라이브러리

4.3.1 libuv가 비동기 I/O를 처리하는 방식
4.3.2 libuv가 블로킹 I/O를 처리하는 방식
4.3.3 I/O를 넘어선 libuv 기능
4.4 사용자 코드로 메인 스레드 탈출하기

4.4.1 워커 스레드
4.4.2 클러스터
4.4.3 자식 프로세스
4.5 이 챕터의 출처

4.5.1 감사의 인사', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO
    `LIKE` (user_id, article_id, create_date)
VALUES
    (2, 4, CURRENT_TIMESTAMP),
    (2, 6, CURRENT_TIMESTAMP),
    (2, 2, CURRENT_TIMESTAMP),
    (3, 2, CURRENT_TIMESTAMP),
    (3, 4, CURRENT_TIMESTAMP),
    (3, 7, CURRENT_TIMESTAMP);

