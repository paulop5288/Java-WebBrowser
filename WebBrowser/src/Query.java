// This file should be assessed for Stage 2

import java.util.Set;

public interface Query {
	public Set<WebDoc> matches(WebIndex wind);
}
