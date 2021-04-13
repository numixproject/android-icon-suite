/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.numix.icons_circle.activity;

import java.util.Locale;

import com.numix.icons_circle.R;
import com.numix.icons_circle.fragment.IconFragmentAll;

import android.os.Bundle;


import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;


public class AllIcons extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle savedInstanceState) {
        MaterialSection home = newSection("Numix Circle", new IconFragmentAll());

        // Set Drawer Header Image
        setDrawerHeaderImage(R.drawable.background);

        // Define new sections
        this.addSection(home);

        this.addSection(this.newSection("Back home", new MainActivity()));


    }

    @Override
    protected void onStart() {
        super.onStart();

        // set the indicator for child fragments
        // N.B. call this method AFTER the init() to leave the time to instantiate the ActionBarDrawerToggle
        this.setHomeAsUpIndicator(R.drawable.ic_action_ic_arrow_back_24px);
    }

    @Override
    public void onHomeAsUpSelected() {
        // when the back arrow is selected this method is called

    }
}