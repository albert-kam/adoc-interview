package coda;

public interface CodaOutWriter {
	public static abstract class Builder<T extends Builder<T>> {
		public abstract CodaOutWriter build();
	}

	WriteRecordResults write(ConvertedCodaCsvRecords recs);
}
