package com.itemis.maven.plugins.unleash.scm.requests;

public class TagRequest {
  protected String message;
  protected boolean push;
  protected String tagName;
  protected boolean commitBeforeTagging;
  protected String preTagCommitMessage;

  TagRequest() {
    // use builder!
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getMessage() {
    return this.message;
  }

  public boolean push() {
    return this.push;
  }

  public String getTagName() {
    return this.tagName;
  }

  public boolean commitBeforeTagging() {
    return this.commitBeforeTagging;
  }

  public String getPreTagCommitMessage() {
    return this.preTagCommitMessage;
  }

  public static class Builder {
    private TagRequest request = new TagRequest();

    public Builder message(String message) {
      this.request.message = message;
      return this;
    }

    public Builder push() {
      this.request.push = true;
      return this;
    }

    public Builder tagName(String tagName) {
      this.request.tagName = tagName;
      return this;
    }

    public Builder commitBeforeTagging() {
      this.request.commitBeforeTagging = true;
      return this;
    }

    public Builder preTagCommitMessage(String message) {
      this.request.preTagCommitMessage = message;
      return this;
    }

    public TagRequest build() {
      return this.request;
    }
  }
}