package cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ScopeType;

@Command(name = "cli")
public class Main implements Runnable {

  @Option(names = "--url", scope = ScopeType.INHERIT, defaultValue = "${env:URL}")
  String url;

  @Override
  public void run() {
    System.out.println("Use a subcommand");
  }

  public static CommandLine createCommandLine() {
    CommandLine commandLine = new CommandLine(new Main());
    commandLine.addSubcommand("list", new ListCommand());
    return commandLine;
  }

  public static void main(String[] args) {
    createCommandLine().execute(args);
  }
}
