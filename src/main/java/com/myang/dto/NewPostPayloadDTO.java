package com.myang.dto;

import java.util.List;

/**
 * Created by myang on 10/16/15.
 */
public class NewPostPayloadDTO
{
  private String title;
  private List categories;
  private String content;

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public List getCategories()
  {
    return categories;
  }

  public void setCategories(List categories)
  {
    this.categories = categories;
  }

  public String getContent()
  {
    return content;
  }

  public void setContent(String content)
  {
    this.content = content;
  }

  public boolean isValid()
  {
    return title != null && !title.isEmpty() && !categories.isEmpty();
  }
}
