package ezenweb.web.service;

import ezenweb.web.domain.file.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    // * 첨부파일 저장 할 경로 [ 1. 배포 전 vs 2. 배포 후 ]
    String path = "C:\\java\\";

    public FileDto fileupload( MultipartFile multipartFile ){
        log.info("File upload : " + multipartFile);
        log.info("File upload : " + multipartFile.getOriginalFilename());   // 실제 첨부파일 파일명
        log.info("File upload : " + multipartFile.getName());               // input name
        log.info("File upload : " + multipartFile.getContentType());        // 첨부파일 확장자
        log.info("File upload : " + multipartFile.getSize());               // 바이트

        // 1. 첨부파일 존재 확인
        if ( !multipartFile.getOriginalFilename().equals("") ){ // 첨부파일이 존재하면

            // * 만약에 다른 이미지인데 파일이 동일하면 식별이 불가하다!
            String fileName = UUID.randomUUID().toString()+"_"+multipartFile.getOriginalFilename();

            // 2. 경로 + UUID 파일명 조합해서 file 클래스 생성
            File file = new File( path+fileName);
            // 3. 업로드 - multipartFile.transferTo ( 저장할 File 클래스 객체 );
            try { multipartFile.transferTo( file );
            } catch (IOException e) { log.info("file upload fail : " + e );  }
            // 4. 반환
            return FileDto.builder()
                    .originalFilename( multipartFile.getOriginalFilename() )
                    .uuidFile( fileName )
                    .sizeKb( multipartFile.getSize()/1024+"kb")
                    .build();
        }
        return null;
    }

    public void filedownload( String filepath ){


    }

}