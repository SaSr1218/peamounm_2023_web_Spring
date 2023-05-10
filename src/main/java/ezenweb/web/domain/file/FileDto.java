package ezenweb.web.domain.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FileDto {
    private String uuidFile;         // 식별이 포함된 파일명
    private String originalFilename; // 실제 순수 파일명
    private String sizeKb;           // 용량 kb
}
