/*
 *  Copyright (c) 2009, Prasanna Velagapudi <pkv@cs.cmu.edu>
 *  All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in the
 *        documentation and/or other materials provided with the distribution.
 *      * Neither the name of the project nor the
 *        names of its contributors may be used to endorse or promote products
 *        derived from this software without specific prior written permission.
 * 
 *  THIS SOFTWARE IS PROVIDED BY THE PROJECT AND CONTRIBUTORS ''AS IS'' AND ANY
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE PROJECT AND CONTRIBUTORS BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package robotutils.examples;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import robotutils.data.Coordinate;
import robotutils.data.GridMapGenerator;
import robotutils.data.GridMapUtils;
import robotutils.data.IntCoord;
import robotutils.data.StaticMap;
import robotutils.gui.MapPanel;
import robotutils.planning.AStar;
import robotutils.planning.EdgeDistance;
import robotutils.planning.GridDStar;

/**
 * Creates a randomized 2D map and solves a path between two random locations
 * using A-Star search.
 * @author Prasanna Velagapudi <pkv@cs.cmu.edu>
 */
public class DStarPlanning {
    public static Random rnd = new Random();

    public static void main(String args[]) {
        StaticMap sm = GridMapGenerator.createRandomMazeMap2D(100, 100);
        
        MapPanel mp = new MapPanel();
        mp.setMapImage(GridMapUtils.toImage(sm));

        JFrame jf = new JFrame("Map");
        jf.setBounds(10, 10, 810, 610);
        jf.getContentPane().add(mp);
        jf.setVisible(true);

        int[] start = new int[sm.dims()];
        while (sm.get(start) < 0) {
            for (int i = 0; i < sm.dims(); i++) {
                start[i] = rnd.nextInt(sm.size(i));
            }
        }

        int[] goal = new int[sm.dims()];
        while (sm.get(goal) < 0) {
            for (int i = 0; i < sm.dims(); i++) {
                goal[i] = rnd.nextInt(sm.size(i));
            }
        }

        System.out.println("Made Graph: " + Arrays.toString(start) + "->" + Arrays.toString(goal));

        Graphics2D g = (Graphics2D)mp.getGraphics();
        g.setColor(Color.BLUE);
        g.drawRect((int)start[0]-1, (int)start[1]-1, 3, 3);
        g.drawRect((int)goal[0]-1, (int)goal[1]-1, 3, 3);

        GridDStar dstar = new GridDStar(sm, new int[]{0,0}, new int[]{50,30});
        List path = dstar.plan();
        
        System.out.println("Done: " + path);

    }
}