package work.niter.wecoding.user.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "comp_stu")
@Document(indexName = "wecoding", type = "stu")
public class CompStudent {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String stuId;
    private String stuName;
    private String stuGender;
    private String stuDept;
    private String stuPhone;
    private String stuEmail;
    private String stuInfo;
    private String stuClass;
    private String stuStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date stuBirthday;
    private Date stuRegistTime;
    private String stuNation;
    private String stuImg;
    private String stuBigImg;
    private String stuArea;
}

