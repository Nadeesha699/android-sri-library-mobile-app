package com.example.bookstrore;

import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class LibraryActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    RecyclerView recyclerView;

    SearchView search_book;

    CustomAdapter adapter;

    List<ListItem> itemList;

    SQLiteDatabase sqLiteDatabase;
    SQLiteOpenHelper sqLiteOpenHelper;
    Cursor cursor;
    String[] name = new String[6];
    String tName;
    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        recyclerView = findViewById(R.id.recyclerView);
        search_book = findViewById(R.id.search_book);
        itemList = new ArrayList<>();
        sqLiteOpenHelper = new SqliteOpenHelperDB(this);
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        x=0;
        cursor = sqLiteDatabase.query("BOOK",new String[]{"BookName","Author"},null,null,null,null,null);
        while(cursor.moveToNext()){
            itemList.add(new ListItem("DOWNLOAD", cursor.getString(0), "Author", cursor.getString(1), R.drawable.fairytale));
            name[x] = cursor.getString(0);
            x++;
        }


        adapter = new CustomAdapter(this, itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        search_book.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        int data =  getIntent().getIntExtra("data",0);
        tName = getIntent().getStringExtra("name");
        if(data == 1){
            try {
                int r = 0;
                while(r <= x){
                    if(Objects.equals(name[r], tName)){
                        test(tName);
                        r = x;
                    }
                    else{

                    }
                    r++;
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public void test(String name) throws FileNotFoundException, DocumentException {
        AlertDialog.Builder alert = new AlertDialog.Builder(LibraryActivity.this,R.style.choose);

        alert.setTitle("SRI LIBRARY");

        alert.setMessage("Enter name for save (example.pdf): ");

        alert.setIcon(R.drawable.read);

        final EditText editText = new EditText(this);
        alert.setView(editText);
        alert.setCancelable(false).setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sName = editText.getText().toString();
                if(Objects.equals(name, "Cinderella")){
                    String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                    String fileName = sName;
                    File file = new File(directoryPath, fileName);

                    FileOutputStream fOut = null;
                    try {
                        fOut = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Document document = new Document(PageSize.A4);


                    try {
                        PdfWriter.getInstance(document, fOut);
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                    document.open();
                    try {
                        document.add(new Paragraph(" \"Cinderella\":\n" +
                                "\n" +
                                "---\n" +
                                "\n" +
                                "Once upon a time in a faraway kingdom, there lived a kind and gentle young woman named Cinderella. She was a beautiful and compassionate soul, but she had the misfortune of living with her wicked stepmother and two equally wicked stepsisters. They treated Cinderella as their servant, forcing her to do all the household chores while they enjoyed a life of luxury.\n" +
                                "\n" +
                                "Cinderella's only friends were the birds and mice that would visit her while she worked. She would often find solace in their company, singing to them and sharing her dreams of a better life. She longed to escape her dreary existence and attend the royal ball, which was being held at the palace in honor of the prince's search for a bride.\n" +
                                "\n" +
                                "One day, an invitation to the grand ball arrived at their doorstep, but Cinderella's stepmother and stepsisters had no intention of allowing her to go. They were determined to keep her from the prince's attention, fearing she would outshine them. Cinderella was devastated but refused to give up hope.\n" +
                                "\n" +
                                "As the day of the ball approached, Cinderella's stepmother and stepsisters left for the palace without her, leaving her in tears. But then, something magical happened. Her tears caught the attention of her fairy godmother, who appeared in a shimmering light. With a wave of her wand, the fairy godmother transformed Cinderella's rags into a magnificent gown, complete with glass slippers.\n" +
                                "\n" +
                                "\"Remember, my dear,\" her fairy godmother said, \"you must leave the ball before midnight, for when the clock strikes twelve, your beautiful dress will turn back into rags.\"\n" +
                                "\n" +
                                "Overwhelmed with joy and gratitude, Cinderella set off for the palace. When she arrived, she took everyone's breath away, including the prince. They danced the night away, and Cinderella felt like she was living a dream. But, as the clock neared midnight, she remembered her fairy godmother's warning. She fled from the palace, leaving behind a single glass slipper.\n" +
                                "\n" +
                                "The prince was captivated by Cinderella's beauty and kindness and was determined to find her. He searched the kingdom, asking every maiden to try on the glass slipper. When he arrived at Cinderella's house, her wicked stepsisters tried to squeeze their feet into the delicate slipper, but it was too small for them. Then, Cinderella, who had been locked in her room, was allowed to try it on.\n" +
                                "\n" +
                                "The glass slipper fit her perfectly, and her true identity was revealed. The prince was overjoyed to have found the woman he had fallen in love with at the ball. Cinderella forgave her stepmother and stepsisters for their cruelty and, with the prince, lived happily ever after. Their love story became a legend in the kingdom, a testament to the power of kindness and the belief that true love can overcome any obstacle."));
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                    document.close();
                    Toast toast = new Toast(LibraryActivity.this);
                    toast.setText("Download story");
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(Objects.equals(name, "Snow White")){
                    String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                    String fileName = sName;
                    File file = new File(directoryPath, fileName);

                    FileOutputStream fOut = null;
                    try {
                        fOut = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Document document = new Document(PageSize.A4);


                    try {
                        PdfWriter.getInstance(document, fOut);
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                    document.open();
                    try {
                        document.add(new Paragraph("\"Snow White\":\n" +
                                "\n" +
                                "---\n" +
                                "\n" +
                                "Once upon a time in a distant kingdom, there lived a beautiful princess named Snow White. She had skin as white as snow, lips as red as roses, and hair as dark as ebony. Her beauty was known far and wide, but it brought her no joy, for her stepmother, the Queen, was wickedly jealous of her stepdaughter's beauty.\n" +
                                "\n" +
                                "The Queen was an enchantress who practiced dark magic. She possessed a magic mirror that could speak the truth about who was the fairest in the land. Every day, the Queen would ask the mirror, \"Mirror, mirror on the wall, who's the fairest of them all?\" And the mirror would reply, \"You, my Queen, are the fairest.\"\n" +
                                "\n" +
                                "But as Snow White grew older, the mirror's response changed. \"Snow White is the fairest of them all,\" it declared. Enraged by this news, the Queen plotted to get rid of Snow White. She ordered a huntsman to take the princess deep into the forest, far from the kingdom, and kill her. The huntsman, however, couldn't bring himself to do it and allowed Snow White to flee.\n" +
                                "\n" +
                                "Lost and frightened, Snow White wandered deep into the dark forest until she came upon a small cottage. It was the home of seven dwarfs who were miners. They allowed Snow White to stay with them, and she, in turn, kept their cottage clean and tidy.\n" +
                                "\n" +
                                "Back at the castle, the Queen discovered that Snow White was still alive. She concocted a plan to kill her stepdaughter herself. Disguised as an old hag, she offered Snow White a poisoned apple. Snow White, unaware of the Queen's true identity, took a bite of the apple and fell into a deep, death-like slumber. The dwarfs were heartbroken, believing Snow White to be gone forever.\n" +
                                "\n" +
                                "As time passed, a prince from a neighboring kingdom heard of Snow White's beauty and the tragic story of her sleep. He arrived at the dwarfs' cottage and was instantly captivated by her beauty. He leaned down and kissed Snow White, breaking the evil spell. Snow White awoke, and they fell in love at first sight.\n" +
                                "\n" +
                                "The prince took Snow White back to his kingdom, where they were married in a grand ceremony. The Queen's wickedness was revealed, and she met a fitting end. Snow White and the prince ruled their kingdom with wisdom and kindness, and they lived happily ever after.\n" +
                                "\n" +
                                "This tale of love, envy, and the triumph of good over evil became a cherished story passed down through generations, reminding all who heard it that kindness and love can conquer even the darkest of magic."));
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                    document.close();
                    Toast toast = new Toast(LibraryActivity.this);
                    toast.setText("Download story");
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(Objects.equals(name, "Little Red Riding Hood")){
                    String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                    String fileName = sName;
                    File file = new File(directoryPath, fileName);

                    FileOutputStream fOut = null;
                    try {
                        fOut = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Document document = new Document(PageSize.A4);


                    try {
                        PdfWriter.getInstance(document, fOut);
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                    document.open();
                    try {
                        document.add(new Paragraph("\"Little Red Riding Hood\":\n" +
                                "\n" +
                                "---\n" +
                                "\n" +
                                "Once upon a time, in a quaint village, there lived a sweet little girl known for her kindness and her vibrant red riding hood, which she wore everywhere. She was affectionately called \"Little Red Riding Hood\" by everyone who knew her.\n" +
                                "\n" +
                                "One sunny morning, Little Red Riding Hood's mother called her to the kitchen. \"I'm sending you to visit your grandmother,\" she said. \"She's not feeling well, and I've packed a basket of goodies for her. Be sure to stay on the path and not talk to strangers.\"\n" +
                                "\n" +
                                "Little Red Riding Hood promised to be careful and set off on her journey through the woods to her grandmother's cottage. As she strolled along the path, she enjoyed the beauty of the forest and the chirping of the birds. Little did she know that a cunning wolf had spotted her and decided to take advantage of her innocence.\n" +
                                "\n" +
                                "The wolf approached Little Red Riding Hood and struck up a conversation. He asked where she was going, and the innocent girl replied, \"I'm going to visit my grandmother, who lives on the other side of the forest. I have some goodies for her.\"\n" +
                                "\n" +
                                "The wolf, with his sinister plans, came up with a scheme. He suggested that Little Red Riding Hood pick some wildflowers for her grandmother to make her feel better. Excited by the idea, Little Red Riding Hood ventured off the path to gather the flowers.\n" +
                                "\n" +
                                "Meanwhile, the wolf took a shortcut to the grandmother's cottage. He arrived there first, knocked on the door, and called out in a voice he tried to make sound like Little Red Riding Hood's, \"Grandma, it's me, Little Red Riding Hood. I've brought some goodies for you.\"\n" +
                                "\n" +
                                "The elderly grandmother, who was hard of hearing, replied, \"Come in, dear.\" The wolf entered the cottage, where the grandmother lay in bed. In a flash, he gobbled her up, leaving only her clothes behind. Then he put on the grandmother's nightgown, cap, and glasses, and climbed into her bed, waiting for Little Red Riding Hood to arrive.\n" +
                                "\n" +
                                "After a little while, Little Red Riding Hood returned with her basket of goodies and the wildflowers. She was surprised to see her grandmother's appearance, noting that she looked different. \"What big eyes you have, Grandma!\" she exclaimed.\n" +
                                "\n" +
                                "\"All the better to see you with, my dear,\" replied the wolf in the grandmother's disguise.\n" +
                                "\n" +
                                "Little Red Riding Hood continued, \"What big ears you have, Grandma!\"\n" +
                                "\n" +
                                "\"All the better to hear you with, my dear,\" said the wolf.\n" +
                                "\n" +
                                "Finally, Little Red Riding Hood said, \"What big teeth you have, Grandma!\"\n" +
                                "\n" +
                                "At this point, the wolf couldn't contain himself any longer. He leaped out of the bed, revealing his true identity, and snarled, \"All the better to eat you with!\" With that, he lunged at Little Red Riding Hood.\n" +
                                "\n" +
                                "Just as the wolf was about to pounce on her, a brave woodcutter who had been passing by the cottage burst through the door. He had heard the commotion and was ready to help. With a swift swing of his axe, he defeated the wolf, saving Little Red Riding Hood and her grandmother.\n" +
                                "\n" +
                                "The grandmother was released from the wolf's belly unharmed, and they were both overjoyed to be free from the cunning creature's clutches. They thanked the woodcutter profusely for his bravery.\n" +
                                "\n" +
                                "Little Red Riding Hood learned a valuable lesson about the dangers of straying from the path and talking to strangers. From that day forward, she was even more careful on her journeys through the woods. The village praised the woodcutter for his heroism, and the story of Little Red Riding Hood's encounter with the wolf served as a timeless cautionary tale for children everywhere."));
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                    document.close();
                    Toast toast = new Toast(LibraryActivity.this);
                    toast.setText("Download story");
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(Objects.equals(name, "Hansel and Gretel")){
                    String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                    String fileName = sName;
                    File file = new File(directoryPath, fileName);

                    FileOutputStream fOut = null;
                    try {
                        fOut = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Document document = new Document(PageSize.A4);


                    try {
                        PdfWriter.getInstance(document, fOut);
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                    document.open();
                    try {
                        document.add(new Paragraph("\"Hansel and Gretel\":\n" +
                                "\n" +
                                "---\n" +
                                "\n" +
                                "Once upon a time in a small village, there lived a poor woodcutter with his wife and two children, Hansel and Gretel. The family was struggling to make ends meet, and they often went to bed hungry.\n" +
                                "\n" +
                                "One particularly harsh winter, the woodcutter's wife, convinced that they could no longer feed their children, persuaded him to abandon Hansel and Gretel deep in the forest. The woodcutter, torn between his love for his children and the pressure from his wife, reluctantly agreed to the plan.\n" +
                                "\n" +
                                "As the children overheard their parents' conversation, Hansel and Gretel decided to take matters into their own hands. That night, they collected small white pebbles, which they dropped along the path into the forest as they were led deeper into the woods by their father.\n" +
                                "\n" +
                                "The next morning, when their parents abandoned them, Hansel and Gretel used the breadcrumbs to find their way back home, guided by the moonlight. Their parents were shocked to see them return, and the family was temporarily reunited. But the stepmother's heart remained hardened, and she devised a new plan to rid herself of the children.\n" +
                                "\n" +
                                "One evening, she gave the children a meager meal and told them to go into the woods to gather berries. Before they left, she locked the door to ensure they couldn't return. Lost in the forest, Hansel and Gretel stumbled upon a strange house made of gingerbread, candy, and sweets.\n" +
                                "\n" +
                                "Starving and unable to resist the delicious-looking house, they began to nibble at it. To their surprise, the house was owned by a wicked witch who lured children in with her edible home. The witch was delighted to see Hansel and Gretel, for she had been seeking children to cook and eat.\n" +
                                "\n" +
                                "The witch invited them inside and treated them kindly at first, feeding them a hearty meal. But Hansel and Gretel soon realized her true intentions when she locked Hansel in a cage and forced Gretel to become her servant.\n" +
                                "\n" +
                                "As the days passed, the witch grew impatient and decided it was time to cook Hansel and eat him. She ordered Gretel to stoke the oven. Gretel, clever and resourceful, pretended not to know how and asked the witch to demonstrate. When the witch climbed into the oven, Gretel quickly slammed the door shut, trapping her inside.\n" +
                                "\n" +
                                "With the wicked witch defeated, Gretel released her brother from the cage, and they discovered the witch's treasures, including precious gems and gold. They filled their pockets with the riches and made their way back into the forest.\n" +
                                "\n" +
                                "Hansel and Gretel wandered for days until they finally stumbled upon a friendly, talking bird who guided them safely back to their village. They returned home to find their stepmother had passed away, and their father was overjoyed to see them.\n" +
                                "\n" +
                                "With their newfound wealth and the love of their father, Hansel and Gretel lived happily ever after, knowing they would never be abandoned or hungry again. The cautionary tale of the gingerbread house and the clever siblings served as a reminder that wit and resourcefulness can overcome even the most wicked of villains."));
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                    document.close();
                    Toast toast = new Toast(LibraryActivity.this);
                    toast.setText("Download story");
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(Objects.equals(name, "Rumpelstiltskin")){
                    String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                    String fileName = sName;
                    File file = new File(directoryPath, fileName);

                    FileOutputStream fOut = null;
                    try {
                        fOut = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Document document = new Document(PageSize.A4);


                    try {
                        PdfWriter.getInstance(document, fOut);
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                    document.open();
                    try {
                        document.add(new Paragraph(" \"Rumpelstiltskin\":\n" +
                                "\n" +
                                "---\n" +
                                "\n" +
                                "Once upon a time, in a small village, there lived a poor miller who was known for his tall tales. He boasted to the king that his daughter could spin straw into gold. The king, who was known for his greed, summoned the miller and his daughter to the palace, eager to exploit her supposed talent.\n" +
                                "\n" +
                                "The miller's daughter was terrified, for she had no magical abilities and had no idea how to turn straw into gold. The king locked her in a room filled with straw and demanded that she spin it into gold by morning, threatening to execute her and her father if she failed.\n" +
                                "\n" +
                                "Alone in the room and weeping at her impending doom, the miller's daughter was visited by a strange little man named Rumpelstiltskin. He appeared before her and asked what she would give him in return for his assistance. Desperate and having nothing to offer, she promised him her firstborn child if he could turn the straw into gold.\n" +
                                "\n" +
                                "Rumpelstiltskin agreed to help and spun the straw into gold with his magical abilities. The next morning, the king was delighted with the gold, but his greed grew even stronger. He moved the miller's daughter to an even larger room filled with straw and demanded she spin it into gold once more, or face dire consequences.\n" +
                                "\n" +
                                "That night, Rumpelstiltskin returned to assist the miller's daughter again, and she, in her desperation, promised him her firstborn child once more. He spun the straw into gold, and the king was pleased. But the miller's daughter knew that she could not continue giving her child away.\n" +
                                "\n" +
                                "Some time later, the miller's daughter married the king and eventually gave birth to a beautiful baby. Rumpelstiltskin returned to claim the child as per their agreement. The miller's daughter was distraught, and she begged him to spare her child. Rumpelstiltskin, with a touch of mercy, told her that if she could guess his name within three days, she could keep her child.\n" +
                                "\n" +
                                "The miller's daughter spent the next two days sending messengers far and wide to learn the strange man's name. On the third day, a messenger returned with the information that they had overheard a strange little man singing to himself in the forest, \"Today I bake, tomorrow I brew, the next I'll have the young queen's child. Ha! Glad am I that no one knew that Rumpelstiltskin I am styled.\"\n" +
                                "\n" +
                                "Armed with this knowledge, the miller's daughter confronted Rumpelstiltskin, who was shocked that she had discovered his name. She declared his name, and he, realizing he had been outsmarted, left in a fury, never to be seen again.\n" +
                                "\n" +
                                "The miller's daughter was able to keep her child and raise it in peace, knowing that she had outwitted the cunning Rumpelstiltskin. The tale serves as a reminder that wit and resourcefulness can triumph over even the most magical of bargains."));
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                    document.close();
                    Toast toast = new Toast(LibraryActivity.this);
                    toast.setText("Download story");
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(Objects.equals(name, "Sleeping Beauty")){
                    String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                    String fileName = sName;
                    File file = new File(directoryPath, fileName);

                    FileOutputStream fOut = null;
                    try {
                        fOut = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Document document = new Document(PageSize.A4);


                    try {
                        PdfWriter.getInstance(document, fOut);
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                    document.open();
                    try {
                        document.add(new Paragraph("\"Sleeping Beauty\":\n" +
                                "\n" +
                                "---\n" +
                                "\n" +
                                "Once upon a time in a magnificent kingdom, there was a grand celebration at the palace. A king and queen had just welcomed their long-awaited daughter into the world, and they invited guests from far and wide to celebrate her birth. They named her Aurora, which means \"dawn.\"\n" +
                                "\n" +
                                "Among the invited guests were three good fairies who blessed the baby princess with special gifts. The first fairy granted her the gift of beauty, the second fairy gave her the gift of a beautiful singing voice, and the third fairy was about to bestow the gift of kindness when the celebration was interrupted.\n" +
                                "\n" +
                                "A wicked fairy, who had not been invited to the celebration, entered the palace in a fit of jealousy. Furious that she was left out, she placed a terrible curse on the infant princess. She decreed that on Aurora's sixteenth birthday, she would prick her finger on the spindle of a spinning wheel and fall into a deep, eternal sleep from which she could only be awakened by true love's kiss.\n" +
                                "\n" +
                                "The king and queen, heartbroken by the curse, ordered all the spinning wheels in the kingdom to be destroyed and outlawed the use of spindles. They were determined to protect their beloved daughter.\n" +
                                "\n" +
                                "As years passed, Princess Aurora grew into a beautiful, kind, and gentle young woman, completely unaware of the curse that had been placed upon her. Her parents had kept her in seclusion within the palace, hoping to protect her from harm. However, on her sixteenth birthday, fate intervened.\n" +
                                "\n" +
                                "While wandering through the palace, Aurora came across a hidden room in the highest tower. Inside, she found an old spinning wheel with a spindle. Curiosity got the better of her, and she touched the spindle, pricking her finger and immediately falling into a deep sleep, just as the wicked fairy had foretold.\n" +
                                "\n" +
                                "The king and queen were devastated, but the good fairies who had blessed Aurora had not forgotten her. They put the entire kingdom to sleep, so it would remain unchanged until the day Aurora was awakened.\n" +
                                "\n" +
                                "Years passed, and a dense forest grew around the palace, hiding it from the outside world. Tales of the sleeping princess and the impenetrable thorns surrounding the castle became legends.\n" +
                                "\n" +
                                "One day, a handsome prince heard the story of the sleeping beauty and the impenetrable forest. He was determined to rescue Aurora and traveled through the thorns, battling the thickets and briars. When he finally reached the castle, he was captivated by the slumbering beauty and kissed her gently.\n" +
                                "\n" +
                                "As their lips met, the curse was broken, and Aurora awakened. The entire kingdom came to life once again, and the prince and Aurora fell in love. They celebrated their love with a grand wedding, uniting their two kingdoms and living happily ever after.\n" +
                                "\n" +
                                "The tale of Sleeping Beauty serves as a reminder of the power of love and how it can break even the most potent of curses. It remains a beloved story of hope and true love's enduring strength."));
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                    document.close();
            }
        }


        }).setNegativeButton("cancle",null).show();

    }


}
