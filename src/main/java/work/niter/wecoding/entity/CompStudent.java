package work.niter.wecoding.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "comp_stu")
@Document(indexName = "wecoding", type = "stu")
public class CompStudent {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String stuId;
    private String stuName;
    private String stuPhone;
    private String stuEmail;
    private String stuClass;
    private String stuNation;
    private String stuStatus;
    private String stuInfo;
    private String stuDept;
    private String stuGender;
}

