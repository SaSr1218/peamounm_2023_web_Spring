package ezenweb.web.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration // 컴포넌트 등록 [ 주요 : Service , Controller , RestController , Repository , Configuration ... ]
@EnableWebSocket // WS 프로토콜 연결 , 웹 소켓 연결
public class WebSocketConfiguration implements WebSocketConfigurer { // websocket 매핑 잡는 역할

    @Autowired // 컴포넌트에 등록한 클래스이므로 @Autowired 가능! = [ DI ]
    private ChattingHandler chattingHandler;

    @Override // 서버소켓으로 사용되고 있는 클래스들 등록
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler( chattingHandler , "/chat").setAllowedOrigins("*");
        // registry.addHandler( 서버소켓 객체 , "서버소켓 URL/path") : 서버소켓 등록 함수
            // .setAllowedOrigins("접속 허용 도메인") : 해당 서버소켓으로부터 요청할 수 있는 URL/도메인
    }

}
