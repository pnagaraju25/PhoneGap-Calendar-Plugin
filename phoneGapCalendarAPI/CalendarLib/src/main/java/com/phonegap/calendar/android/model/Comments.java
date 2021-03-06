/*
 * Copyright (c) 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/*
 *  Modified by Sergio Martinez. Copyright 2011 Vodafone Group Services Ltd.
 * 
 */

package com.phonegap.calendar.android.model;

import com.google.api.client.util.Key;

/**
 * Represents comments of a GData feed or entry.
 * 
 * @author Alain Vongsouvanh
 * @author Nicolas Garnier
 * @author Sergio Martinez Rodriguez
 */
public class Comments {

  /** Regular comments */
  public static final String TYPE_REGULAR = "http://schemas.google.com/g/2005#regular";

  /** Professional reviews */
  public static final String TYPE_REVIEWS = "http://schemas.google.com/g/2005#reviews";

  /** Comments feed. This feed should implement the Message kind. */
  @Key("gd:feedLink")
  public FeedLink feedLink;
}
