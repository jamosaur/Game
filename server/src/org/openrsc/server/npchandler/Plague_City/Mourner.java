/**
* Generated By NPCScript :: A scripting engine created for openrsc by Zilent
*/
//npc ID 444
package org.openrsc.server.npchandler.Plague_City;
import org.openrsc.server.event.DelayedQuestChat;
import org.openrsc.server.event.SingleEvent;
import org.openrsc.server.model.ChatMessage;
import org.openrsc.server.model.InvItem;
import org.openrsc.server.model.MenuHandler;
import org.openrsc.server.model.Npc;
import org.openrsc.server.model.Player;
import org.openrsc.server.model.Quest;
import org.openrsc.server.model.World;
import org.openrsc.server.npchandler.NpcHandler;



public class Mourner implements NpcHandler
 {

	public void handleNpc(final Npc npc, final Player owner) throws Exception
	{
		npc.blockedBy(owner);
		owner.setBusy(true);
		Quest q = owner.getQuest(35);
		if(q != null) 
		{
			if(q.finished()) 
			{
				noQuestStarted(npc, owner);
			}
			else 
			{
				switch(q.getStage())
				{
					default:
						noQuestStarted(npc, owner);
				}
			}
		} 
		else 
		{
			noQuestStarted(npc, owner);
		}
	}
	
	private void noQuestStarted(final Npc npc, final Player owner)
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Hello"}, true)
		{
			public void finished()
			{
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"What do you want?"})
				{
					public void finished()
					{	
						World.getDelayedEventHandler().add(new SingleEvent(owner, 2000)
						{
							public void action()
							{
								final String[] options107 = {"Who are you?", "Nothing just being polite"};
								owner.setBusy(false);
								owner.sendMenu(options107);
								owner.setMenuHandler(new MenuHandler(options107) 
								{
									public void handleReply(final int option, final String reply)
									{
										owner.setBusy(true);
										for(Player informee : owner.getViewArea().getPlayersInView())
										{
											informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
										}
										switch(option) 
										{
											case 0:
												whoAreYou(npc, owner);
											break;
											case 1:
												owner.setBusy(false);
												npc.unblock();
											break;
										}
									}
								});
							}
						});	
					}	
				});			
			}
		});
	}
	
	private void whoAreYou(final Npc npc, final Player owner) 
	{
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"I'm a mourner", "It's my job to help heal the plague victims of west ardougne", "and to make sure the disease is contained"}) 
		{
			public void finished()
			{
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Who pays you?"}) 
				{
					public void finished()
					{
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"We feel as the kings henchmen it's our duty to help the people of ardougne"}) 
						{
							public void finished()
							{
								World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Very noble of you"}) 
								{
									public void finished()
									{
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"If you come down with any symptoms such as a flu or nightmares", "Let me know immediately"}) 
										{
											public void finished()
											{
												owner.setBusy(false);
												npc.unblock();
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});
	}
	
}