package main.game.sprites.tasks;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import main.game.sprites.Sprite;
import main.saving.DataTag;
import main.utils.helper.JavaHelp;

public class TaskMap
{
	public final List<AITaskBase> tasks;
	public final Sprite owner;

	public TaskMap(Sprite owner)
	{
		this.owner = owner;
		tasks = JavaHelp.newArrayList();
	}

	public void addTask(AITaskBase task)
	{
		if (!tasks.contains(task))
		{
			task.disabled = false;
			tasks.add(task);
		}
	}

	public void removeTask(AITaskBase task)
	{
		task.disabled = true;
		tasks.remove(task);
	}

	public void updateAITasks()
	{
		Collections.sort(tasks, new Comparator<AITaskBase>()
		{
			@Override
			public int compare(AITaskBase o1, AITaskBase o2)
			{
				return Integer.compare(o1.priority, o2.priority);
			}
		});
		for (int i = 0; i < tasks.size(); i++)
		{
			final AITaskBase task = tasks.get(i);
			if (task.priority < 0)
			{
				task.disabled = true;
			}
			if (!task.disabled)
			{
				task.executeTask();
			}
		}
	}

	public void saveTasks(DataTag tag)
	{
		for (int i = 0; i < tasks.size(); i++)
		{
			final DataTag tag1 = new DataTag();
			tasks.get(i).saveToTag(tag1);
			tag.setTag("" + i, tag1);
		}
	}

	public void loadTasks(DataTag tag)
	{
		for (int i = 0; i < tasks.size(); i++)
		{
			final DataTag tag1 = tag.getTag("" + i);
			tasks.get(i).loadFromTag(tag1);
		}
	}

	public int size()
	{
		return tasks.size();
	}
}
