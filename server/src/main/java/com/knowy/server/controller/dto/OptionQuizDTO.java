import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionQuizDTO {
	private int courseID;
	private int lessonID;
	private int quizID;
	private String letter;
	private String text;
	private boolean correct;
}
