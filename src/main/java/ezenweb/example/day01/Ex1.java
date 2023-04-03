package ezenweb.example.day01;

public class Ex1 {
    public static void main(String[] args) {
        // 1. 일반 dto
        Dto dto = new Dto( 1 , "qwe" , "qwe" , 100 , "010-4444-4444");

        // 2.1 lombok [ @NoArgsConstructor ] -> 빈 생성자 제공
        LombokDto lombokDto1 = new LombokDto();
        // 2.2 lombok [ AllArgsConstructor ] -> 풀 생성자 제공
        LombokDto lombokDto2 = new LombokDto(1,"qwe","qwe",100,"010-4444-4444");
        // 2.3 lombok [ @Setter @Getter ]
        System.out.println("getter : " + lombokDto1.getMid() );
        lombokDto1.setMid("asd");
        // 2.4 lombok [ @ToString ]
        System.out.println("toString : " + lombokDto1.toString() );
        // 2.5 lombok [ @Builder ]
        LombokDto lombokDto3 = LombokDto.builder()
                .mid("qwe")
                .mpassword("qwe").build();
        System.out.println("toString : " + lombokDto3.toString() );
            // 생성자 안쓰고 아이디와 패스워드가 저장된 객체 생성
            // 객체 생성시 매개변수 개수 상관 X 순서 상관 X









    }
}
