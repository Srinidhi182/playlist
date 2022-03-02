import java.util.*;
import java.util.ArrayList;
import java.util.LinkedList;
class Song {
    private String title;
    private double duration;
    public Song(String title, double duration) {
        this.title = title;
        this.duration = duration;
        }
    public String getTitle() {
        return title;
    }
    @Override
    public String toString() {
        return this.title + " : " + this.duration;
    }
}
class Album {
    public String name;
    public String artist;
    private SongList songs;
    public Album(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.songs = new SongList();
    }
    public boolean addSong(String title, double duration) {
        return this.songs.add(new Song(title, duration));
    }

    public boolean addToPlaylist(int trackNumber, LinkedList<Song> playlist) {
        Song checkedSong = this.songs.findSong(trackNumber);
        if (checkedSong != null) {
            playlist.add(checkedSong);
            return true;
        }
        System.out.println("This album does not have a track" + trackNumber);
        return false;
    }

    public boolean addToPlaylist(String title, LinkedList<Song> playList) {
        Song checkedSong = songs.findSong(title);
        if (checkedSong != null) {
            playList.add(checkedSong);
            return true;
        }
        System.out.println("The song , " + title + " , is not in this album.");
        return false;
    }

    private class SongList {

        private ArrayList<Song> songs;

        public SongList() {
            this.songs = new ArrayList<Song>();
        }

        public boolean add(Song song) {
            if (songs.contains(song)) {
                return false;
            }
            this.songs.add(song);
            return true;
        }

        private Song findSong(String title) {
            for (Song checkedSong : this.songs) {
                if (checkedSong.getTitle().equals(title)) {
                    return checkedSong;
                }
            }
            return null;
        }

        public Song findSong(int trackNumber) {
            int index = trackNumber - 1;
            if ((index >= 0) && (index < songs.size())) {
                return songs.get(index);
            }
            return null;
        }

    }
}

public class Main {
    private static ArrayList<Album> albums = new ArrayList<Album>();

    public static void main(String[] args) {
        Album album = new Album("Stormbringer", "Deep Purple");
        album.addSong("Love Don't Mean a thing", 4.22);
        album.addSong("Stormbringer", 4.6);
        album.addSong("Hold on", 5.6);
        album.addSong("The Gypsy", 3.6);
        albums.add(album);

        album = new Album("Numb", "Linkin Park");
        album.addSong("Castle of Glass", 4.3);
        album.addSong("Numb", 4.67);
        album.addSong("New Divide", 5.6);

        albums.add(album);

        LinkedList<Song> playList = new LinkedList<Song>();

        albums.get(0).addToPlaylist("Stormbringer", playList);
        albums.get(0).addToPlaylist("Hold on", playList);
        albums.get(0).addToPlaylist(0, playList);
        albums.get(0).addToPlaylist(1, playList);
        albums.get(0).addToPlaylist(2, playList);
        albums.get(0).addToPlaylist(3, playList);

        albums.get(1).addToPlaylist(1, playList);
        albums.get(1).addToPlaylist(2, playList);
        albums.get(1).addToPlaylist(3, playList);

        play(playList);

    }

    private static void play(LinkedList<Song> playlist) {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        boolean forward = true;
        ListIterator<Song> listIterator = playlist.listIterator();
        if (playlist.size() == 0) {
            System.out.println("No songs in the Playlist");
        } else {
            System.out.println("Now Playing - " + listIterator.next().toString());
            printMenu();
        }
        while (!quit) {
            int action = scanner.nextInt();
            scanner.nextLine();
            switch (action) {
                case 0:
                    System.out.println("Playlist Complete.");
                    quit = true;
                    break;
                case 1:
                    if (!forward) {
                        if (listIterator.hasNext()) {
                            listIterator.next();
                        }
                        forward = true;
                    }
                    if (listIterator.hasNext()) {
                        System.out.println("Now Playing " + listIterator.next().toString());
                    } else {
                        System.out.println("We have reached the end of the playlist");
                        forward = false;
                    }
                    break;
                case 2:
                    if (forward) {
                        if (listIterator.hasPrevious()) {
                            listIterator.previous();
                        }
                        forward = false;
                    }
                    if (listIterator.hasPrevious()) {
                        System.out.println("Now Playing " + listIterator.previous().toString());
                    } else {
                        System.out.println("We have reached the end of the playlist");
                        forward = true;
                    }
                    break;
                case 3:
                    if (forward) {
                        if (listIterator.hasPrevious()) {
                            System.out.println("Now Replaying " + listIterator.previous().toString());
                            forward = false;
                        } else {
                            System.out.println("We are at the start of the playlist");
                        }
                    } else {
                        if (listIterator.hasNext()) {
                            System.out.println("Now Replaying " + listIterator.next().toString());
                            forward = true;
                        } else {
                            System.out.println("We are at the start of the playlist");
                        }
                    }
                    break;
                case 4:
                    printList(playlist);
                    break;
                case 5:
                    printMenu();
                    break;
                case 6:
                    if (playlist.size() > 0) {
                        listIterator.remove();
                        if (listIterator.hasNext()) {
                            System.out.println("Now Playing " + listIterator.next());
                        } else if (listIterator.hasPrevious()) {
                            System.out.println("Now Playing " + listIterator.previous());
                        }
                    }
                    break;

            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("Available Actions : \n Press ");
        System.out.println("0. To Quit \n1. To Play the next Song.\n" +
                "2. To Play the Previous Song\n3.To replay the Current Song\n" +
                "4. To list the songs in the playlist\n5. To print the available actions." +
                "\n6.To Remove the current Song from the playlist");
    }

    private static void printList(LinkedList<Song> playlist) {
        Iterator<Song> iterator = playlist.iterator();
        System.out.println("=================");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("===============");
    }
}