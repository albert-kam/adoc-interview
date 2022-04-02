package coda;

import static com.google.common.base.Preconditions.checkArgument;

import javax.annotation.processing.Generated;

public class FieldRuleString implements CodaFieldRule {
	private boolean required;
	
	@Generated("SparkTools")
	private FieldRuleString(Builder builder) {
		required = builder.required;
	}
	@Override
	public String validate(String value) {
		checkArgument(value != null);
		if (required) {
			checkArgument(!value.isEmpty());
		}
		return value;
	}

	/**
	 * Creates builder to build {@link FieldRuleString}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link FieldRuleString}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private boolean required;

		private Builder() {
		}

		public Builder withRequired(boolean required) {
			this.required = required;
			return this;
		}

		public FieldRuleString build() {
			return new FieldRuleString(this);
		}
	}

}
