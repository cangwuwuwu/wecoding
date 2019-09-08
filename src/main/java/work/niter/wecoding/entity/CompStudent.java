package work.niter.wecoding.entity;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "compasso_stu")
public class CompStudent {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long stuId;
    private String stuName;
    private String stuPhone;
    private String stuEmail;
    private String stuInfo;
    private String stuDept;
    private String stuGender;
}

