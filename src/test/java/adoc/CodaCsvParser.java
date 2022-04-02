package adoc;

import java.io.File;

public interface CodaCsvParser {
	public static abstract class Builder<T extends Builder<T>> {
		public abstract CodaCsvParser build();
	}

	CodaCsvRecords parse(File file);
}
