package adoc;

import static com.google.common.base.Preconditions.checkArgument;

import javax.annotation.processing.Generated;

public class FieldRuleString implements CodaFieldRule {
	
	@Generated("SparkTools")
	private FieldRuleString(Builder builder) {
	}

	@Override
	public String validate(String value) {
		checkArgument(value != null);
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
		private Builder() {
		}

		public FieldRuleString build() {
			return new FieldRuleString(this);
		}
	}

}
