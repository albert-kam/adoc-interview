package coda;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.regex.Pattern;

import javax.annotation.processing.Generated;

public class FieldRulePhone implements CodaFieldRule {
	private String pattern;

	@Generated("SparkTools")
	private FieldRulePhone(Builder builder) {
		pattern = checkNotNull(builder.pattern);
	}
	
	@Override
	public String validate(String value) {
		checkArgument(value != null);
		checkArgument(isValidPhoneNumberFormat(value));
		return value;
	}

	// 810-292-9388
	private boolean isValidPhoneNumberFormat(String value) {
		return Pattern.compile(pattern)
				.matcher(value)
				.matches();
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
		private String pattern;

		private Builder() {
		}

		public Builder withPattern(String pattern) {
			this.pattern = pattern;
			return this;
		}

		public FieldRulePhone build() {
			return new FieldRulePhone(this);
		}
	}

}
