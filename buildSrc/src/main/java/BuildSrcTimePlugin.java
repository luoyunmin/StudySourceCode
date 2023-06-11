import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

public class BuildSrcTimePlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        target.task("buildSrcTimePlugin").doLast(new Action<Task>() {
            @Override
            public void execute(Task task) {
                System.out.println("this is buildSrcTimePlugin");
            }
        });
    }
}
