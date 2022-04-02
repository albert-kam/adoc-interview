package adoc;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class ProjectContext  {

	private static volatile ProjectContext instance;
	private Map<Version, ProjectFactory> factories;

	private ProjectContext() {
		factories = new ImmutableMap.Builder<Version, ProjectFactory>()
				.put(Version.V1, ProjectFactoryV1.get())
				.put(Version.V2, ProjectFactoryV2.get())
				.build();
	}

	public static ProjectContext get() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (ProjectContext.class) {
				if (instance == null) {
					instance = new ProjectContext();
				}
			}
		}
		return instance;
	}

	public ProjectFactory getFactory(Version version) {
		return checkNotNull(factories.get(version));
	}
	
}
