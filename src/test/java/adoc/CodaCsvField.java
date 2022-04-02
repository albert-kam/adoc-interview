package adoc;

import javax.annotation.processing.Generated;

public class CodaCsvField {
	private String col;
	private CodaFieldRule rule;
	
	@Generated("SparkTools")
	private CodaCsvField(Builder builder) {
		col = builder.col;
		rule = builder.rule;
	}
	/**
	 * Creates builder to build {@link CodaCsvField}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link CodaCsvField}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String col;
		private CodaFieldRule rule;

		private Builder() {
		}

		public Builder withCol(String col) {
			this.col = col;
			return this;
		}

		public Builder withRule(CodaFieldRule rule) {
			this.rule = rule;
			return this;
		}

		public CodaCsvField build() {
			return new CodaCsvField(this);
		}
	}
	
	public static CodaCsvField of(String col, CodaFieldRule rule) {
		return CodaCsvField.builder()
				.withCol(col)
				.withRule(rule)
				.build();
	}
	public String getCol() {
		return col;
	}
	public CodaFieldRule getRule() {
		return rule;
	}
}
