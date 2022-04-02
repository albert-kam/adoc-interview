package coda;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.processing.Generated;

import com.google.common.collect.Sets;

public class FieldRuleEmail implements CodaFieldRule {
	private Set<String> excludedDomains;
	
	@Override
	public String validate(String value) {
		checkArgument(value != null);
		checkArgument(isValidEmail(value));
		if (!excludedDomains.isEmpty()) {
			checkArgument(!excludedDomains.stream()
				.anyMatch(excludedDomain -> value.endsWith(excludedDomain)));
		}
		return value;
	}

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
	@Generated("SparkTools")
	private FieldRuleEmail(Builder builder) {
		excludedDomains = builder.excludedDomains;
	}
	private boolean isValidEmail(String email) {
		if (email == null || email.isEmpty()) {
			return false;
		}
		return EMAIL_PATTERN.matcher(email)
				.matches();
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
		private Set<String> excludedDomains = Collections.emptySet();

		private Builder() {
		}

		public Builder withExcludedDomains(String...excludedDomains) {
			this.excludedDomains = Sets.newHashSet(excludedDomains);
			return this;
		}

		public FieldRuleEmail build() {
			return new FieldRuleEmail(this);
		}
	}

}
