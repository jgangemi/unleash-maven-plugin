package com.itemis.maven.plugins.unleash.scm;

import java.io.File;

import com.google.common.base.Optional;
import com.itemis.maven.plugins.unleash.scm.annotations.ScmProviderType;
import com.itemis.maven.plugins.unleash.scm.requests.CommitRequest;
import com.itemis.maven.plugins.unleash.scm.requests.DeleteTagRequest;
import com.itemis.maven.plugins.unleash.scm.requests.TagRequest;
import com.itemis.maven.plugins.unleash.scm.requests.UpdateRequest;

/**
 * SCM providers for the unleash-maven-plugin must implement this interface to provide SCM-specific access for the
 * plugin.
 * Furthermore the providers must be annotated witht the {@link ScmProviderType} annotation providing the appropriate
 * SCM type name.
 *
 * @author <a href="mailto:stanley.hillner@itemis.de">Stanley Hillner</a>
 * @since 0.1.0
 */
public interface ScmProvider {
  // TODO also provide an option for keyfiles, ...
  // system or repo keyring?

  /**
   * Initializes the SCM provider with the working directory and some other optional parameters such as credentials.
   *
   * @param workingDirectory the working directory on which the scm provider has to do its work.
   * @param username the username for remote SCM access.
   * @param password the password for remote SCM access.
   */
  void initialize(File workingDirectory, Optional<String> username, Optional<String> password);

  /**
   * Closes the SCM provider and releases all bound resources.
   */
  void close();

  /**
   * Commits the specified paths or the whole working directory changes. For distributed SCMs a push to the remote
   * repository can be specified. If there are remote changes the provider tries to commit the local changes or fails if
   * there are conflicts.
   *
   * @param request the request containing all relevant settings for the commit.
   * @return the new revision number after the commit which is the remote revision or the new local revision for
   *         distributed SCMs when pushing is not enabled.
   * @throws ScmException if either the commit or the push to the remote repo encountered an error.
   */
  String commit(CommitRequest request) throws ScmException;

  /**
   * Pushes the local changes to the remote repository which is relevant for distributed SCMs only.
   *
   * @throws ScmException if the push encountered an error, f.i. if the remote repo is ahead, ...
   */
  void push() throws ScmException;

  /**
   * Updates the local repository with changes of the remote repository which might fail due to conflicts. Merging can
   * also be required.
   *
   * @param request the request describing what to update to which revision and how to deal with conflicts.
   * @return the remote revision to which the working directory was updated. This revision is also the new local
   *         revision.
   * @throws ScmException if the update fails, f.i. due to unresolvable conflicts.
   */
  String update(UpdateRequest request) throws ScmException;

  /**
   * Creates a tag on the local (and optionally the remote) repository.
   *
   * @param request the request specifying all relevant information for the tag creation.
   * @return the new revision number after the tag has been created which is the remote revision or the new local
   *         revision for
   *         distributed SCMs when pushing is not enabled.
   * @throws ScmException if the tag could not be created.
   */
  String tag(TagRequest request) throws ScmException;

  /**
   * @param tagName the name of the tag which is searched.
   * @return <code>true</code> if the repository contains a tag with the specified name.
   * @throws ScmException if the repository access (local or remote) fails and no exact information about the presence
   *           of the tag can be given.
   */
  boolean hasTag(String tagName) throws ScmException;

  /**
   * Deletes a tag from the repository. In case of distributed SCMs the deletion can only happen locally or also on the
   * remote repository if pushing is enabled.
   *
   * @param request the deletion request specifying all relevant information for tag deletion.
   * @return the new revision number after the tag deletion which is the remote revision or the new local revision for
   *         distributed SCMs when pushing is not enabled.
   * @throws ScmException if the tag could not be deleted.
   */
  String deleteTag(DeleteTagRequest request) throws ScmException;

  /**
   * @return the revision of the current working copy.
   */
  String getLocalRevision();

  /**
   * @return the latest remote revision. In case of distributed SCMs the remote revision is retrieved from the remote
   *         the current branch is tracking.
   */
  String getLatestRemoteRevision();

  /**
   * Calculates the URL of a tag with the given tag name from another connection URL which might just be a basic
   * URL or a URL containing branch info, ...
   *
   * @param currentConnectionString a connection URL from which the tag URL shall be derived assuming standard
   *          directory layout.
   * @param tagName the name of the branch.
   * @return the calculated tag URL.
   */
  String calculateTagConnectionString(String currentConnectionString, String tagName);

  /**
   * Calculates the URL of the branch with the given branch name from another connection URL which might just be a basic
   * URL or a URL containing branch info, ...
   *
   * @param currentConnectionString a connection URL from which the branch URL shall be derived assuming standard
   *          directory layout.
   * @param branchName the name of the branch.
   * @return the calculated branch URL.
   */
  String calculateBranchConnectionString(String currentConnectionString, String branchName);

  /**
   * @return <code>true</code> if the SCM connection URL comprises also the tag name (and path).
   */
  boolean isTagInfoIncludedInConnection();
}
