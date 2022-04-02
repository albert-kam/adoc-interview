package coda;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.processing.Generated;

import com.google.common.collect.Lists;

public class AppCsv2json {
	private List<Consumer<File>> fileValidators;

	@Generated("SparkTools")
	private AppCsv2json(Builder builder) {
		fileValidators = checkNotNull(builder.fileValidators);
	}

	/**
	 * Creates builder to build {@link AppCsv2json}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link AppCsv2json}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private List<Consumer<File>> fileValidators = Collections.emptyList();

		private Builder() {
		}

		public Builder withFileValidators(List<Consumer<File>> fileValidators) {
			this.fileValidators = fileValidators;
			return this;
		}

		public AppCsv2json build() {
			return new AppCsv2json(this);
		}
	}

	public File loadFile(String path) {
		File file = new File(path);
		fileValidators.stream()
			.forEach(validator -> validator.accept(file));
		return file;
	}
}
