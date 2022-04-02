package coda;

import static com.google.common.base.Preconditions.checkArgument;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.regex.Pattern;

import javax.annotation.processing.Generated;

public class FieldRuleNumber implements CodaFieldRule {
	private boolean integerMode;

	@Generated("SparkTools")
	private FieldRuleNumber(Builder builder) {
		integerMode = builder.integerMode;
	}
	
	@Override
	public String validate(String value) {
		checkArgument(value != null);
		checkArgument(integerMode ? isValidInteger(value) : isValidNumeric(value));
		return value;
	}

	private static final Pattern IS_DIGIT = Pattern.compile("[-+]?[0-9]+");
	private boolean isValidInteger(String value) {
		return IS_DIGIT.matcher(value).matches();
	}

	private boolean isValidNumeric(String str) {
		ParsePosition pos = new ParsePosition(0);
		NumberFormat.getInstance()
			.parse(str, pos);
		return str.length() == pos.getIndex();
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
		private boolean integerMode;

		private Builder() {
		}

		public Builder withIntegerMode(boolean integerMode) {
			this.integerMode = integerMode;
			return this;
		}

		public FieldRuleNumber build() {
			return new FieldRuleNumber(this);
		}
	}

}
