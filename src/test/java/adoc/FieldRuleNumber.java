package adoc;

import static com.google.common.base.Preconditions.checkArgument;

import javax.annotation.processing.Generated;

public class FieldRuleNumber implements CodaFieldRule {

	@Generated("SparkTools")
	private FieldRuleNumber(Builder builder) {
	}

	@Override
	public String validate(String value) {
		checkArgument(value != null);
		return value;
	}

	/**
	 * Creates builder to build {@link FieldRuleNumber}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link FieldRuleNumber}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Builder() {
		}

		public FieldRuleNumber build() {
			return new FieldRuleNumber(this);
		}
	}

}
