
package com.stackroute.musicservice.service;
        import com.stackroute.musicservice.domain.Track;
        import java.util.List;

        import com.stackroute.musicservice.exception.GlobalException;
        import com.stackroute.musicservice.exception.TrackAlreadyExistsException;
        import com.stackroute.musicservice.exception.TrackNotFoundException;
        import com.stackroute.musicservice.repository.TrackRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.cache.annotation.*;
        import org.springframework.context.annotation.Primary;
        import org.springframework.stereotype.Service;

@Service
@Primary
@EnableCaching
@CacheConfig(cacheNames = "track")
public class TrackServiceImpl implements TrackService {

    TrackRepository trackRepository;

    public void simulateDelay(){
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

   // @CacheEvict(allEntries= true)
   @Override
   public Track saveTrack(Track track) throws TrackAlreadyExistsException{
       if(trackRepository.existsById(track.getTrackId()))
       {
           throw new TrackAlreadyExistsException("Track already exists");
       }
       Track savedUser=trackRepository.save(track);

       if(savedUser==null)
       {
           throw new TrackAlreadyExistsException("Track already exists");
       }

       return savedUser;
   }

    @Cacheable
    @Override
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

 /*   @CacheEvict(allEntries= true)
    @Override
    public Track getTrackById(int id) throws TrackNotFoundException {
        Track track = null;
        if (trackRepository.existsById(id)) {
            track = trackRepository.getOne(id);
        } else {
            throw new TrackNotFoundException("Track not exists");
        }
        if (track == null) {
            throw new TrackNotFoundException("Track not found");
        }
        return track;
    }
    @CacheEvict(allEntries= true)
    @Override
    public Track getByName(String trackName) throws TrackNotFoundException {
        Track track = null;
        track = trackRepository.getByName(trackName);
        if (track == null) {
            throw new TrackNotFoundException("Track name not found");
        }
        return track;
    }
     */

    @CacheEvict(allEntries= true)
    @Override
    public Track deleteTrack(int trackId) throws TrackNotFoundException {
        if (trackRepository.existsById(trackId)) {
            trackRepository.deleteById(trackId);
        } else {
            throw new TrackNotFoundException("track not found");
        }
        return null;
    }
    @CachePut("track")
    @Override
    public Track updateComments(int id, Track track) throws GlobalException {
        if (trackRepository.existsById(id)) {
            track.setTrackComments(track.getTrackComments());
        } else {
            throw new GlobalException();
        }
        Track track1 = trackRepository.save(track);
        return track1;

    }
}
