package jp.gn.gonchan;

import jp.gn.gonchan.dto.cokkie.CokkieInfo;
import jp.gn.gonchan.filters.CokkieInfoFactory;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.skife.jdbi.v2.DBI;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import javax.inject.Singleton;

/**
 * Dropwizard based application launcher.
 */
public class Matome extends Application<MatomeConfiguration> {

    /**
     * Java entry point.
     *
     * @param args the command-line arguments.
     * @throws Exception an error occurred.
     */
    public static void main(String[] args) throws Exception {
        new Matome().run(args);
    }

    /**
     * @see io.dropwizard.Application#initialize(io.dropwizard.setup.Bootstrap)
     */
    @Override
    public void initialize(Bootstrap<MatomeConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<MatomeConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(MatomeConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(new ViewBundle<MatomeConfiguration>() {
            @Override
            public Map<String, Map<String, String>> getViewConfiguration(MatomeConfiguration configuration) {
                return super.getViewConfiguration(configuration);
            }
        });
    }

    /**
     * @see io.dropwizard.Application#run(io.dropwizard.Configuration, io.dropwizard.setup.Environment)
     */
    @Override
    public void run(MatomeConfiguration configuration, Environment environment) {
        // Common modules
        environment.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
                // Configuration
                bind(configuration).to(MatomeConfiguration.class);

                // Dao
                final DBIFactory jdbiFactory = new DBIFactory();
                final DBI jdbi = jdbiFactory.build(environment, configuration.getDataSourceFactory(), "h2database");
                registerModules("jp.gn.gonchan.dao", "Dao", (classInfo) -> {
                    @SuppressWarnings("unchecked")
                    Class<Object> daoClass = (Class<Object>) classInfo.load();
                    bind(jdbi.onDemand(daoClass)).to(daoClass);
                });

                // Logics
                registerModules("jp.gn.gonchan.logics", "Logic", (classInfo) -> {
                    bindAsContract(classInfo.load()).in(Singleton.class);
                });

                bindFactory(CokkieInfoFactory.class).to(CokkieInfo.class).in(RequestScoped.class);
            }
        });

        // Resources
        registerModules("jp.gn.gonchan.resources", "Resource", (classInfo) -> {
            environment.jersey().register(classInfo.load());
        });

        // Filter
        registerModules("jp.gn.gonchan.filters", "Feature", (classInfo) -> {
            environment.jersey().register(classInfo.load());
        });

        // Servlet Filter
        // environment.servlets().addFilter("TopRedirectFilter", new TopRedirectFilter())
        // .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

    }

    private void registerModules(String packageName, String classNameSuffix, Consumer<? super ClassInfo> action) {
        try {
            ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClassesRecursive(packageName)
                    .stream().filter((classInfo) -> classInfo.getName().endsWith(classNameSuffix)).forEach(action);
        } catch (IOException ex) {
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }

}
