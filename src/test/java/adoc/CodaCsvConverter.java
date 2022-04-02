package adoc;

public interface CodaCsvConverter {

	public static abstract class Builder<T extends Builder<T>> {
		public abstract CodaCsvConverter build();
	}

	public ConvertedCodaCsvRecords convert(CodaCsvRecords recs);

}
