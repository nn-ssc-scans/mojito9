package com.box.l10n.mojito.cli.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.box.l10n.mojito.cli.console.ConsoleWriter;
import com.box.l10n.mojito.phabricator.Phabricator;
import com.box.l10n.mojito.phabricator.payload.Data;
import com.box.l10n.mojito.phabricator.payload.RevisionSearchFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Parameters(
    commandNames = {"phab-targetphid-to-revisionid"},
    commandDescription = "Get the revision id for a target phid")
public class PhabricatorRevisionCommand extends Command {
  /** logger */
  static Logger logger = LoggerFactory.getLogger(PhabricatorRevisionCommand.class);

  @Qualifier("ansiCodeEnabledFalse")
  @Autowired
  ConsoleWriter consoleWriterAnsiCodeEnabledFalse;

  @Autowired(required = false)
  Phabricator phabricator;

  @Parameter(
      names = {"--target-phid"},
      arity = 1,
      required = true,
      description = "target phid")
  String targetPhid = null;

  @Override
  public boolean shouldShowInCommandList() {
    return false;
  }

  @Override
  public void execute() throws CommandException {
    PhabricatorPreconditions.checkNotNull(phabricator);
    Data<RevisionSearchFields> revisionForTargetPhid =
        phabricator.getRevisionForTargetPhid(targetPhid);
    consoleWriterAnsiCodeEnabledFalse.a(revisionForTargetPhid.getId()).println();
  }
}
