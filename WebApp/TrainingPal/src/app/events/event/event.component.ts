import {Component, OnDestroy, OnInit} from '@angular/core';
import * as SimpleWebRTC from 'simplewebrtc';
import {AuthService} from '../../_services/auth.service';
import {ChatMessage} from '../../_models/chat-message.model';
import {AlertService} from '../../_services/alert.service';
import {ActivatedRoute, Router} from '@angular/router';
import {EventService} from '../../_services/event.service';
import {EventResponse} from '../../_models/response/event-response.model';
import {Event} from '../../_models/event.model';
import {ErrorResponse} from '../../_models/response/error-response.model';
declare const jquery: any;
declare const $: any;

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit, OnDestroy {

  webrtc: SimpleWebRTC;
  messages: ChatMessage[] = [];
  enteredMessage: string;

  displayLocalVideo: boolean;
  displayRemoteVideo: boolean;
  displayLiveStreamChatBox: boolean;
  displayOneToOneChatBox: boolean;

  isVideoPaused: boolean;
  isAudioMuted: boolean;
  isInCall: boolean;

  isEventOneToOne: boolean;
  isEventVideoConf: boolean;

  isUserCoach: boolean;
  isUserAthlete: boolean;

  canJoinCall: boolean;

  roomName: string;

  currentUser: string;
  remoteUser: string;

  eventId: string;

  pageName: string;

  currentEvent: Event;

  constructor(
    private authService: AuthService,
    private alertService: AlertService,
    private route: ActivatedRoute,
    private router: Router,
    private eventService: EventService
  ) { }

  ngOnInit() {

    this.eventId = this.route.snapshot.params['event_id'];

    this.eventService.getEvent(Number(this.eventId)).subscribe(
      (response: EventResponse) => {
        this.currentEvent = response.Data.Event;

        if (this.currentEvent.Type.toString() === '1') {

          this.isEventOneToOne = true;
          this.pageName = 'One to One';
          this.initValues();

        } else if (this.currentEvent.Type.toString() === '2') {

          this.isEventVideoConf = true;
          this.pageName = 'Video Conference';
          this.initValues();

        } else {
          this.router.navigate(['not-found']);
        }

        alert('This Event Is Scheduled For *** ' + new Date(this.currentEvent.Event_Date).toDateString() + ' ***');
      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);
        this.router.navigate(['not-found']);
      }
    );
  }

  initValues() {
    this.roomName = 'app.trainingpal.me/' + this.eventId;
    this.currentUser = this.authService.getCurrentAuthObject().UserId;

    this.isVideoPaused = false;
    this.isAudioMuted = false;
    this.isInCall = false;

    this.canJoinCall = false;

    this.isUserCoach = this.authService.isUserCoach();
    this.isUserAthlete = this.authService.isUserAthlete();

    this.displayLiveStreamChatBox = false;
    this.displayOneToOneChatBox = false;

    // Setting default items to display

    if (this.isEventOneToOne) {
      this.displayLocalVideo = true;
      this.displayRemoteVideo = true;
    }

    if (this.isEventVideoConf) {
      if (this.isUserAthlete) {
        this.displayLocalVideo = false;
        this.displayRemoteVideo = true;
      }

      if (this.isUserCoach) {
        this.displayLocalVideo = true;
        this.displayRemoteVideo = false;
      }
    }

    this.webRTCInit(this.authService.getCurrentAuthObject().UserId);
  }

  ngOnDestroy() {
    this.webrtc.stopLocalVideo();
    this.webrtc.leaveRoom();
    this.webrtc.disconnect();
  }

  sendMessage() {
    const content = this.enteredMessage === 'undefined' ? '' : this.enteredMessage;
    this.addMessage(this.authService.getCurrentAuthObject().UserId, 0 , content);
    this.webrtc.sendDirectlyToAll('meta', 'chatMessage', {'data': {'from': this.authService.getCurrentAuthObject().UserId, 'content': content}});
    this.enteredMessage = '';
  }

  sendJoinedMessage() {
    this.webrtc.sendDirectlyToAll('meta', 'joinMessage', {'data': {'from': this.authService.getCurrentAuthObject().UserId, 'content': 'has joined!'}});
  }

  sendLeftMessage() {
    this.webrtc.sendDirectlyToAll('meta', 'leftMessage', {'data': {'from': this.authService.getCurrentAuthObject().UserId, 'content': 'has left!'}});
  }

  addMessage(username: string, messageType: number, messageContent: string) {
    const message = new ChatMessage();
    message.from = username;
    message.type = messageType;
    message.content = messageContent;

    this.messages.push(message);
  }

  changeCallStatus() {
    if (this.isInCall) {

      this.isInCall = false;
      this.displayOneToOneChatBox = false;
      this.displayLiveStreamChatBox = false;
      this.sendLeftMessage();
      this.messages = [];
      this.webrtc.leaveRoom();

    } else {

      this.isInCall = true;
      if (this.isEventOneToOne) { this.displayOneToOneChatBox = true; }
      if (this.isEventVideoConf) { this.displayLiveStreamChatBox = true; }
      this.webrtc.joinRoom(this.roomName);

    }
  }

  changeAudio(isMuted: boolean) {
    if (this.isAudioMuted) {
      this.isAudioMuted = false;
    } else {
      this.isAudioMuted = true;
    }

    if (this.isAudioMuted) {
      this.webrtc.mute();
    } else {
      this.webrtc.unmute();
    }
  }

  changeVideo() {
    if (this.isVideoPaused) {
      this.isVideoPaused = false;
    } else {
      this.isVideoPaused = true;
    }

    if (this.isVideoPaused) {
      this.webrtc.pauseVideo();
    } else {
      this.webrtc.resumeVideo();
    }
  }

  webRTCInit(username: string) {
    let webrtc;

    const defaultOptions = {
      localVideoEl: 'localVideo',
      remoteVideosEl: 'remoteVideos',
      autoRequestMedia: true,
      detectSpeakingEvents: true,
      debug: false,
      nick: username
    };

    const noMediaOptions = {
      localVideoEl: 'localVideo',
      remoteVideosEl: 'remoteVideos',
      autoRequestMedia: true,
      media: { audio: false, video: false },
      debug: false,
      nick: username
    };

    if (this.isEventOneToOne) { webrtc = new SimpleWebRTC(defaultOptions); }
    if (this.isEventVideoConf && this.isUserCoach) { webrtc = new SimpleWebRTC(defaultOptions); }
    if (this.isEventVideoConf && this.isUserAthlete) { webrtc = new SimpleWebRTC(noMediaOptions); }

    this.webrtc = webrtc;

    // when it's ready, join if we got a room from the URL
    webrtc.on('readyToCall', () => {
      // you can name it anything
       this.canJoinCall = true;
       this.alertService.success('You\'re ready to join!');
    });

    webrtc.on('createdPeer', (peer) => {
      setTimeout(() => {
        this.addMessage(peer.nick, 2, 'has joined!');
        } , 3000);
    });

    // a peer video has been added
    webrtc.on('videoAdded', (video, peer) => {
      this.remoteUser = peer.nick;
    });

    // a peer was removed
    webrtc.on('videoRemoved', (video, peer) => {
      this.remoteUser = '';
    });

    // local p2p/ice failure
    webrtc.on('iceFailed', (peer) => {
      this.alertService.error('An Error has occurred, please try again!');
    });

    // remote p2p/ice failure
    webrtc.on('connectivityError', (peer) => {
      this.alertService.error('An Error has occurred, please try again!');
    });

    webrtc.on('channelMessage', (oPeer, sLabel, oData) => {
      if ('meta' === sLabel) {
        if ('chatMessage' === oData.type) {
          const content = oData.payload.data.content === 'undefined' ? '' : oData.payload.data.content;
          this.addMessage(oData.payload.data.from, 1, content);
        }
        if ('joinedMessage' === oData.type) {
          const content = oData.payload.data.content === 'undefined' ? '' : oData.payload.data.content;
          this.addMessage(oData.payload.data.from, 2, content);
        }
        if ('leftMessage' === oData.type) {
          const content = oData.payload.data.content === 'undefined' ? '' : oData.payload.data.content;
          this.addMessage(oData.payload.data.from, 2, content);
        }
      }
    });
  }
}
