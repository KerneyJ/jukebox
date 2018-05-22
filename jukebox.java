package com.jukebox;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.scene.media.AudioClip;
import javafx.stage.FileChooser;

import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;


public class jukebox{
    private static int songNum = 0;
    private static ArrayList<AudioClip> songList;
    private static String song;
    private static int songs;
    private JPanel panel1;
    private JButton jB_play;
    private JButton jB_pause;
    private JSlider js_volume;
    private JButton Back;
    private JButton Next;
    private JLabel jl_songName;
    private JTextField jt_amountSongs;
    private JTextArea jt_songList;

    public jukebox(){
        js_volume.setValue(0);
        songList = new ArrayList<AudioClip>();
        jt_songList.setEditable(false);

        jB_play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //System.out.println("play");
                songList.get(songNum).play();
            }
        });
        jB_pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //System.out.println("pause");
                songList.get(songNum).stop();
            }
        });
        js_volume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                songList.get(songNum).setVolume(10000 * (double)js_volume.getValue());

            }
        });
        Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                songList.get(songNum).stop(); // stop current song
                songNum++; // increase song index
                if(songNum >= songList.size()){
                    songNum = 0;
                }
                //System.out.println("Next " + songNum); // output for error checking
                song = songList.get(songNum).getSource().substring(songList.get(songNum).getSource().lastIndexOf("/") + 1); // get the name of the new song
                jl_songName.setText(song); // set the title of the song
            }
        });
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                songList.get(songNum).stop(); // stop current song
                songNum--; // increase song index
                if(songNum < 0){
                    songNum = 0;
                }
                //System.out.println("Next " + songNum); // output for error checking
                song = songList.get(songNum).getSource().substring(songList.get(songNum).getSource().lastIndexOf("/") + 1); // get the name of the new song
                jl_songName.setText(song); // set the title of the song
            }
        });
        jt_amountSongs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jt_amountSongs.setEditable(false);
                songs = Integer.parseInt(jt_amountSongs.getText());
                jt_amountSongs.setText(String.valueOf(songs) + " songs");
                for (int i = 0; i < songs; i++) {
                    String fName = com.jukebox.FileChooser.pickAFile();
                    AudioClip ac = new AudioClip(new File(fName).toURI().toString());
                    songList.add(ac);
                }
                // place current song on display
                song = songList.get(songNum).getSource().substring(songList.get(songNum).getSource().lastIndexOf("/") + 1); // get the name of the new song
                jl_songName.setText(song); // set the title of the song

                // Create a song list
                String songs = "";
                for(AudioClip ac: songList){
                    songs += ac.getSource().substring(ac.getSource().lastIndexOf("/") + 1) + "\n";
                }
                jt_songList.setText(songs);

            }
        });
        jt_amountSongs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                jt_amountSongs.setText("");
            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Jukebox");
        frame.setContentPane(new jukebox().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
