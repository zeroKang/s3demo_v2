package org.zerock.s3demo.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@ToString
@Getter
@Setter
public class S3Component {

    private final AmazonS3Client s3Client;





}
