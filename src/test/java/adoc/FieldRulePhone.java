package adoc;

import static com.google.common.base.Preconditions.checkArgument;

import javax.annotation.processing.Generated;

public class FieldRulePhone implements CodaFieldRule {

	@Generated("SparkTools")
	private FieldRulePhone(Builder builder) {
	}

	/**
	 * Creates builder to build {@link FieldRulePhone}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link FieldRulePhone}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Builder() {
		}

		public FieldRulePhone build() {
			return new FieldRulePhone(this);
		}
	}

	@Override
	public String validate(String value) {
		checkArgument(value != null);
		return value;
	}

}
