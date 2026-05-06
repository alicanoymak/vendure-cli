package cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ScopeType;

@Command(
    name = "cli",
    subcommands = {ListCommand.class})
public class Main implements Runnable {

  @Option(names = "--url", scope = ScopeType.INHERIT, defaultValue = "${env:URL}")
  String url;

  @Override
  public void run() {
    System.out.println("Use a subcommand");
  }

  public static void main(String[] args) {
    new CommandLine(new Main()).execute(args);
  }
}
