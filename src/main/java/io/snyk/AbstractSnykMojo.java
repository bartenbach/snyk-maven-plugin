package io.snyk;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractSnykMojo extends AbstractMojo {

    @Parameter(property = "apiToken")
    protected String apiToken;

    @Parameter(property = "cli")
    protected CLI cli;

    @Parameter(property = "args")
    protected List<String> args;

    public void execute() throws MojoFailureException, MojoExecutionException {
        getLog().info("args:");
        getLog().info(args.toString());

        ProcessBuilder commandLine = CommandLine.asProcessBuilder(
            cli.getExecutable(),
            this.getCommand(),
            Optional.ofNullable(apiToken),
            args
        );

        CommandRunner.run(commandLine::start, getLog());
    }

    public abstract Command getCommand();
}
