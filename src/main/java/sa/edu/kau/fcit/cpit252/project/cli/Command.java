package sa.edu.kau.fcit.cpit252.project.cli;

import java.sql.SQLException;

public interface Command {
    void execute() throws SQLException;
}
