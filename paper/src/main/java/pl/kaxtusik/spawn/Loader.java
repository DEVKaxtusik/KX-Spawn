package pl.kaxtusik.spawn;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

public class Loader implements PluginLoader {
    @Override
    public void classloader(PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();

        resolver.addRepository(new RemoteRepository.Builder("papermc-repo", "default", "https://repo.papermc.io/repository/maven-public/").build());
        resolver.addRepository(new RemoteRepository.Builder("sonatype", "default", "https://oss.sonatype.org/content/groups/public/").build());
        resolver.addRepository(new RemoteRepository.Builder("panda-lang", "default", "https://repo.panda-lang.org/releases").build());
        resolver.addRepository(new RemoteRepository.Builder("okaeri", "default", "https://storehouse.okaeri.eu/repository/maven-public/").build());
        resolver.addRepository(new RemoteRepository.Builder("jitpack", "default", "https://jitpack.io").build());

        resolver.addDependency(new Dependency(new DefaultArtifact("dev.rollczi:litecommands-bukkit:3.10.2"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("eu.okaeri:okaeri-configs-yaml-bukkit:5.0.9"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("eu.okaeri:okaeri-configs-serdes-bukkit:5.0.9"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.projectlombok:lombok:1.18.38"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("com.github.cryptomorin:XSeries:13.3.3"), null));

        classpathBuilder.addLibrary(resolver);
    }
}
