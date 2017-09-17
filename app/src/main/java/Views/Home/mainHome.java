package Views.Home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.linedeer.api.EventCall;
import com.linedeer.api.call;
import com.linedeer.player.Ui;
import com.player.playerEvents;
import com.shape.home.backgroundImg;
import com.shape.home.btmStick;
import com.shape.home.equlizerIcon;
import com.shape.home.facebookIcon;
import com.shape.home.folderIcon;
import com.shape.home.mainBtnBack;
import com.shape.home.nextBtn;
import com.shape.home.prevBtn;
import com.shape.home.settingBtn;


import Views.ContentHome;
import Views.Home.Equalizer.eqlizerMain;
import Views.Home.Equalizer.eqlizerMenu;
import Views.Home.views.playBtn;
import Views.Home.views.playListBtn;
import Views.api.FMText;
import Views.api.FMlyt;
import Views.api.ShapeView;
import Views.textImg;

public class mainHome extends FMlyt{

	thumbSlider ImgSlider;
	songSlider SongSlider;
	playListBtn PlayListBtn;
	ShapeView SongItemsBtn;

	textImg PlayListText;
	textImg PlayListName;


	public static String FACEBOOK_URL = "https://www.facebook.com/linedeer.apps/";
	public static String FACEBOOK_PAGE_ID = "linedeer.apps";

	//method to get the right URL to use in the intent
	public String getFacebookPageURL(Context context) {
		PackageManager packageManager = context.getPackageManager();
		try {
			int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
			if (versionCode >= 3002850) { //newer versions of fb app
				return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
			} else { //older versions of fb app
				return "fb://page/" + FACEBOOK_PAGE_ID;
			}
		} catch (PackageManager.NameNotFoundException e) {
			return FACEBOOK_URL; //normal web url
		}
	}

	public mainHome(final Context context, final int width, final int height) {
		super(context, width, height);
		setEnableCatch();
		setBackgroundColor(backgroundImg.Color0);

		int ImgSliderHt = (int)(height/100F*60);
		int ImgSliderY = (int)(height/100F*16);
		ImgSlider = new thumbSlider(context,width,ImgSliderHt);
		//ImgSliderHt = width;
		ImgSlider.setY(ImgSliderY);
		addView(ImgSlider);

		int SongSliderY = ImgSliderHt + ImgSliderY;
		SongSlider = new songSlider(context,width, Ui.cd.getHt(22));
		SongSlider.setY(SongSliderY - Ui.cd.getHt(12));
		addView(SongSlider);


		int PlayBtnY = ((height - SongSliderY) - Ui.cd.getHt(88));
		int PlayBtnX = (int) ((width - Ui.cd.getHt(53)) / 2f);
		PlayBtnY = PlayBtnY / 2;
		PlayBtnY = PlayBtnY + SongSliderY;
		playBtn pb = new playBtn(context, Ui.cd.getHt(53), Ui.cd.getHt(88));
		pb.setY(PlayBtnY);
		pb.setRipple(true );
		pb.setX(PlayBtnX);
		addView(pb);

		ShapeView nextbtn = nextBtn.getFMview(getContext(),true);
		nextbtn.setX(PlayBtnX + pb.width + Ui.cd.getHt(5));
		nextbtn.setY(PlayBtnY + ((pb.height - nextbtn.height) / 2f));
		nextbtn.setRipple(true);
		addView(nextbtn);
		nextbtn.onClick(new call(){
			@Override
			public void onCall(boolean bl) {
				Ui.ef.MusicPlayer.handler.playNext();
			}
		});


		ShapeView prevbtn = prevBtn.getFMview(getContext(),true);
		prevbtn.setX(PlayBtnX - (prevbtn.width + Ui.cd.getHt(5)));
		prevbtn.setY(PlayBtnY + ((pb.height - nextbtn.height) / 2f));
		prevbtn.setRipple(true);
		addView(prevbtn);
		prevbtn.onClick(new call(){
			@Override
			public void onCall(boolean bl) {
				Ui.ef.MusicPlayer.handler.playPrevious();
			}
		});

		int PlayListBtnY = (ImgSliderY - Ui.cd.getHt(64)) / 2;
		PlayListBtn = new playListBtn(context, Ui.cd.getHt(28), Ui.cd.getHt(64));
		PlayListBtn.setY((ImgSliderY - Ui.cd.getHt(64)) / 2);
		PlayListBtn.onClick(new call(){
			@Override
			public void onCall(boolean bl) {
				ContentHome.This.openPlaylist();
			}
		});


		SongItemsBtn = settingBtn.getFMview(context,true);
		int SongItemsBtnY = (ImgSliderY - SongItemsBtn.height) / 2;
		SongItemsBtn.setRipple(true,0.2f);
		SongItemsBtn.setY(SongItemsBtnY);
		SongItemsBtn.setX((width - SongItemsBtn.width));
		addView(SongItemsBtn);
		SongItemsBtn.onClick(new call(){
			@Override
			public void onCall(boolean bl) {
				ContentHome.This.openQEq();
			}
		});

		ShapeView facebook = facebookIcon.getFMview(context,true);
		facebook.setRipple(true,0.2f);
		facebook.InCenter(SongItemsBtn);
		facebook.setX((SongItemsBtn.getX() - facebook.width  - Ui.cd.getHt(10)));
		addView(facebook);
		facebook.onClick(new call(){
			@Override
			public void onCall(boolean bl) {
				/*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/photo.php?fbid=612811792203439&set=a.106730739478216.17472.100004238705306&type=3&permPage=1"));
				Ui.ef.startActivity(browserIntent);*/
				/*Intent intent;
					try {
						context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
						intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/linedeer.apps"));
					} catch (Exception e) {
						intent =  new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/linedeer.apps"));
					}
				Ui.ef.startActivity(intent);*/
				/*String link = getFacebookPageURL(getContext());
				Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
				facebookIntent.setData(Uri.parse(link));
				Ui.ef.startActivity(facebookIntent);*/
				String facebookUrl = "https://www.facebook.com/linedeer.apps/";
				try {
					int versionCode = context.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
					if (versionCode >= 3002850) {
						Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
						Ui.ef.startActivity(new Intent(Intent.ACTION_VIEW, uri));;
					} else {
						// open the Facebook app using the old method (fb://profile/id or fb://page/id)
						Ui.ef.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/171101266630991")));
					}
				} catch (PackageManager.NameNotFoundException e) {
					// Facebook is not installed. Open the browser
					Ui.ef.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
				}
			}
		});


		final FMText Text = textImg.getFMText(context,"NOW PLAYING (SONG(300))", Ui.cd.getHt(14));
		FMText listText = textImg.getFMText(context,"LINEDEER MUSIC PLAYER", Ui.cd.getHt(18));
		listText.height += Text.height + Ui.cd.getHt(10);
		listText.InCenter(SongItemsBtn);
		listText.setText("MUSIC PLAYER BY LINEDEER");
		listText.img.setColor(mainBtnBack.Color0);
		listText.setX(Ui.cd.getHt(24));
		addView(listText);

		addView(PlayListBtn);
		//Text.InCenter(SongItemsBtn);
		Text.img.setColor(0x66cccccc);

		Text.setY(listText.getY() + listText.height + Ui.cd.getHt(10));
		Text.setX(listText.getX());
		Text.setSize((int) (Ui.cd.DPW -  Ui.cd.getHt(120)) ,Text.height);
		//Text.setBackgroundColor(0x33FFFFFF);
		Text.img.setEfects(new int[]{0x66cccccc,0x66cccccc,0x00cccccc});
		addView(Text);


		PlayListText = new textImg(width, Ui.cd.getHt(20), Ui.cd.getHt(30),PlayListBtnY + Ui.cd.getHt(12), Ui.cd.getHt(18));
		PlayListText.setColor(mainBtnBack.Color0);
		PlayListText.setText("BY LINDEER.");

		PlayListName = new textImg(width, Ui.cd.getHt(20), Ui.cd.getHt(30),PlayListBtnY + Ui.cd.getHt(14+20), Ui.cd.getHt(14));
		PlayListName.setColor(0xFF9388B9);
		PlayListName.setText("NOW PLAYING ( Daily Music )");

		ShapeView sh = btmStick.getFMview(context,true);
		sh.setX((width - sh.width) / 2f);
		sh.setY((height - sh.height));
		addView(sh);

		ShapeView equlizer = equlizerIcon.getFMview(context,true);
		equlizer.setRipple(true,0.3f);
		equlizer.setY(height - equlizer.height - Ui.cd.getHt(20));
		equlizer.onClick(new call(){
			@Override
			public void onCall(boolean bl) {
				openEqulizer();
			}
		});
		equlizer.setX(Ui.cd.getHt(20));
		addView(equlizer);

		equlizer.onClick(new call(){
			@Override
			public void onCall(boolean bl) {
				final eqlizerMenu eq = new eqlizerMenu(getContext(),width, (int) (height * 0.7f));
				eq.setY(Ui.cd.DPH);
				ContentHome.This.addPopup(eq);
				eq.open(Ui.cd.DPH - eq.height,true);
				Ui.bk.add(new call(){
					@Override
					public void onCall(boolean bl) {
						//super.onCall(bl);
						eq.stopAnim();
						ContentHome.This.removePopup(eq);
						ContentHome.This.MainHome.removeCatch();
						ContentHome.This.MainHome.setAlpha(1,false);
					}
				});
			}
		});


		ShapeView folder = folderIcon.getFMview(context,true);
		folder.setRipple(true,0.3f);
		folder.setRippleDown(false);
		folder.setY(height - folder.height - Ui.cd.getHt(20));
		folder.setX(width - folder.width - Ui.cd.getHt(20));
		addView(folder);

		folder.onClick(new call(){
			@Override
			public void onCall(boolean bl) {
				ContentHome.openlibMenu();
			}
		});
		//openEqulizer();

		Ui.ef.playerEvent.addEvent(new EventCall(new int[]{Ui.ef.Event_onBind,playerEvents.PLAYLIST_CHANGED,playerEvents.SONG_CHANGED}){
			@Override
			public void onCall(int eventId) {
				if(Ui.ef.MusicPlayer.handler.playlist.listName != null){
					Ui.ef.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Text.img.setText("NOW PLAYING : "+ Ui.ef.MusicPlayer.handler.playlist.listName);
							Text.invalidate();
						}
					});
				}
			}
		});

		/*ShapeView ni = notice.getFMview(getContext(),false);
		ni.InCenter(width,height);
		addView(ni);*/
	}

	void openEqulizer(){
		final eqlizerMain Fv = new eqlizerMain(getContext(),width,height);
		ContentHome.This.addPopup(Fv);
		Ui.bk.add(new call() {
			@Override
			public void onCall(boolean bl) {
				ContentHome.This.removePopup(Fv);
			}
		});
	}
}
