package cn.kungreat.seata.utils;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class JdbcUtils {

    private final DataSource dataSource;

    public JdbcUtils(DataSource dataSource) {
        System.out.println(dataSource);
        this.dataSource = dataSource;
    }

    public void runTest(){
        try(Connection connection = dataSource.getConnection()){

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
