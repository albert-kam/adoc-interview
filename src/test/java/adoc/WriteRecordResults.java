package adoc;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.annotation.processing.Generated;

import com.google.common.collect.Lists;

public class WriteRecordResults {
	private List<File> outFiles;

	@Generated("SparkTools")
	private WriteRecordResults(Builder builder) {
		outFiles = builder.outFiles;
	}
	
	public List<File> getOutFiles() {
		return outFiles;
	}

	/**
	 * Creates builder to build {@link WriteRecordResults}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link WriteRecordResults}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private List<File> outFiles = Lists.newArrayList();

		private Builder() {
		}

		public Builder addOutFile(File outFile) {
			outFiles.add(outFile);
			return this;
		}

		public WriteRecordResults build() {
			return new WriteRecordResults(this);
		}
	}

}
