package coda;

import javax.annotation.processing.Generated;

public class CodaCsvRecordCell {
	private String key, value;
	private CodaFieldRule fieldRule;
	
	@Generated("SparkTools")
	private CodaCsvRecordCell(Builder builder) {
		key = builder.key;
		value = builder.value;
		fieldRule = builder.fieldRule;
	}
	/**
	 * Creates builder to build {@link CodaCsvRecords}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link CodaCsvRecords}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String key;
		private String value;
		private CodaFieldRule fieldRule;

		private Builder() {
		}

		public Builder withKey(String key) {
			this.key = key;
			return this;
		}

		public Builder withValue(String value) {
			this.value = value;
			return this;
		}

		public Builder withFieldRule(CodaFieldRule fieldRule) {
			this.fieldRule = fieldRule;
			return this;
		}

		public CodaCsvRecordCell build() {
			return new CodaCsvRecordCell(this);
		}
	}
	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	public CodaFieldRule getFieldRule() {
		return fieldRule;
	}
}
