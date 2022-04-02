package coda;

import java.util.Collections;
import java.util.List;

import javax.annotation.processing.Generated;

import com.google.common.collect.Lists;

public class ConvertedCodaCsvRecords {
	private List<String> convertedRecs;

	@Generated("SparkTools")
	private ConvertedCodaCsvRecords(Builder builder) {
		convertedRecs = builder.convertedRecs;
	}

	/**
	 * Creates builder to build {@link ConvertedCodaCsvRecords}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link ConvertedCodaCsvRecords}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private List<String> convertedRecs = Collections.emptyList();

		private Builder() {
		}

		public Builder withConvertedRecs(List<String> convertedRecs) {
			this.convertedRecs = convertedRecs;
			return this;
		}

		public ConvertedCodaCsvRecords build() {
			return new ConvertedCodaCsvRecords(this);
		}
	}

	public List<String> getConvertedRecs() {
		return Lists.newArrayList(convertedRecs);
	}
}
