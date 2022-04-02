package adoc;

import static com.google.common.base.Preconditions.checkArgument;

import javax.annotation.processing.Generated;

public class FieldRuleEmail implements CodaFieldRule {

	@Generated("SparkTools")
	private FieldRuleEmail(Builder builder) {
	}

	/**
	 * Creates builder to build {@link FieldRuleEmail}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link FieldRuleEmail}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Builder() {
		}

		public FieldRuleEmail build() {
			return new FieldRuleEmail(this);
		}
	}

	@Override
	public String validate(String value) {
		checkArgument(value != null);
		return value;
	}

}
