package com.quizapp01;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.quizapp01.QuizConstants.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuizApp.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("EXPLORE_INDIA");
        insertCategory(c1);
        Category c2 = new Category("COUNTRIES");
        insertCategory(c2);
        Category c3 = new Category("NOVELS");
        insertCategory(c3);
        Category c4 = new Category("PLANETS");
        insertCategory(c4);
    }

    public void addCategory(Category category) {
        db = getWritableDatabase();
        insertCategory(category);
    }

    public void addCategories(List<Category> categories) {
        db = getWritableDatabase();

        for (Category category : categories) {
            insertCategory(category);
        }
    }

    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        //India - Easy
        Question q1 = new Question("What Indian city is the capital of two states?",
                "Chennai", "Chandigarh", "Delhi", 2,
                Question.DIFFICULTY_EASY, Category.EXPLORE_INDIA);
        insertQuestion(q1);
        Question q2 = new Question("How many nations border India?",
                "6", "9", "2", 1,
                Question.DIFFICULTY_EASY, Category.EXPLORE_INDIA);
        insertQuestion(q2);
        Question q3 = new Question("What, approximately, is the area of India, in square kilometers?",
                "3000000", "4000000", "2000000", 1,
                Question.DIFFICULTY_EASY, Category.EXPLORE_INDIA);
        insertQuestion(q3);
        Question q4 = new Question("What is India’s smallest state?",
                "Nagaland", "Manipur", "Goa", 3,
                Question.DIFFICULTY_EASY, Category.EXPLORE_INDIA);
        insertQuestion(q4);
        Question q5 = new Question("Which of these bodies of water does not border India?",
                "Indian Ocean", "Red Sea", "Arabian Sea", 2,
                Question.DIFFICULTY_EASY, Category.EXPLORE_INDIA);
        insertQuestion(q5);


        //Countries
        Question q6 = new Question("Which city is the capital of Canada?",
                "Toronto", "Ottawa", "Montreal", 2,
                Question.DIFFICULTY_EASY, Category.COUNTRIES);
        insertQuestion(q6);
        Question q7 = new Question("Which of these is the largest landlocked country in the world?",
                "Germany", "China", "Kazakhstan", 3,
                Question.DIFFICULTY_EASY, Category.COUNTRIES);
        insertQuestion(q7);
        Question q8 = new Question("What European country is divided into departments?",
                "Sweden", "France", "Switzerland", 2,
                Question.DIFFICULTY_EASY, Category.COUNTRIES);
        insertQuestion(q8);
        Question q9 = new Question("Which of these countries is said to be shaped like an elephant’s head?",
                "Thailand", "Australia", "Zaire", 1,
                Question.DIFFICULTY_EASY, Category.COUNTRIES);
        insertQuestion(q9);
        Question q10 = new Question("Which one is the largest City in Canada ?",
                "Toronto", "Calgary", "Vancouver", 1,
                Question.DIFFICULTY_EASY, Category.COUNTRIES);
        insertQuestion(q10);

        //Novels
        Question q11 = new Question("Which of these novels is by F. Scott Fitzgerald?",
                "The Great Gatsby", "The Great Santini", "Catharine the Great", 1,
                Question.DIFFICULTY_MEDIUM, Category.NOVELS);
        insertQuestion(q11);
        Question q12 = new Question("Which of these novels is by Aldous Huxley?",
                "Brave New World", "Rio Bravo", "Lonely are the Brave", 3,
                Question.DIFFICULTY_MEDIUM, Category.NOVELS);
        insertQuestion(q12);
        Question q13 = new Question("Who wrote the novel The Cost of Living?",
                "Arundhathi Roy", "Jhumpa Lahiri", "Kiran Desai", 1,
                Question.DIFFICULTY_MEDIUM, Category.NOVELS);
        insertQuestion(q13);
        Question q14 = new Question("Which of these novels is by E.M. Forster?",
                "Safe Passage", "A Passage to India", "Northwest Passage", 2,
                Question.DIFFICULTY_MEDIUM, Category.NOVELS);
        insertQuestion(q14);
        Question q15 = new Question("Which of these novels is by Joseph Conrad?",
                "Heart of Darkness", "Braveheart", "Heartbeat", 1,
                Question.DIFFICULTY_MEDIUM, Category.NOVELS);
        insertQuestion(q15);

        //Planets
        Question q16 = new Question("How many planets are there in our solar system?",
                "8", "9", "7", 1,
                Question.DIFFICULTY_HARD, Category.PLANETS);
        insertQuestion(q16);
        Question q17 = new Question("What is the smallest planet in the Solar System?",
                "Neptune", "Mars", "Mercury", 3,
                Question.DIFFICULTY_HARD, Category.PLANETS);
        insertQuestion(q17);
        Question q18 = new Question("What is the largest planet in the Solar System?",
                "Saturn", "Jupiter", "Earth", 2,
                Question.DIFFICULTY_HARD, Category.PLANETS);
        insertQuestion(q18);
        Question q19 = new Question("Phobos and Deimos are moons of what planet?",
                "Venus", "Mars", "Saturn", 2,
                Question.DIFFICULTY_HARD, Category.PLANETS);
        insertQuestion(q19);
        Question q20 = new Question("What is the brightest planet in the night sky?",
                "Venus", "Mars", "Jupiter", 1,
                Question.DIFFICULTY_HARD, Category.PLANETS);
        insertQuestion(q20);

        //India - Medium
        Question q21 = new Question("What Indian city is the capital of two states?",
                "Chennai", "Chandigarh", "Delhi", 2,
                Question.DIFFICULTY_MEDIUM, Category.EXPLORE_INDIA);
        insertQuestion(q21);
        Question q22 = new Question("How many nations border India?",
                "6", "9", "2", 1,
                Question.DIFFICULTY_MEDIUM, Category.EXPLORE_INDIA);
        insertQuestion(q22);
        Question q23 = new Question("What, approximately, is the area of India, in square kilometers?",
                "3000000", "4000000", "2000000", 1,
                Question.DIFFICULTY_MEDIUM, Category.EXPLORE_INDIA);
        insertQuestion(q23);
        Question q24 = new Question("What is India’s smallest state?",
                "Nagaland", "Manipur", "Goa", 3,
                Question.DIFFICULTY_MEDIUM, Category.EXPLORE_INDIA);
        insertQuestion(q24);
        Question q25 = new Question("Which of these bodies of water does not border India?",
                "Indian Ocean", "Red Sea", "Arabian Sea", 2,
                Question.DIFFICULTY_MEDIUM, Category.EXPLORE_INDIA);
        insertQuestion(q25);

        //India - Hard
        Question q26 = new Question("What Indian city is the capital of two states?",
                "Chennai", "Chandigarh", "Delhi", 2,
                Question.DIFFICULTY_HARD, Category.EXPLORE_INDIA);
        insertQuestion(q26);
        Question q27 = new Question("How many nations border India?",
                "6", "9", "2", 1,
                Question.DIFFICULTY_HARD, Category.EXPLORE_INDIA);
        insertQuestion(q27);
        Question q28 = new Question("What, approximately, is the area of India, in square kilometers?",
                "3000000", "4000000", "2000000", 1,
                Question.DIFFICULTY_HARD, Category.EXPLORE_INDIA);
        insertQuestion(q28);
        Question q29 = new Question("What is India’s smallest state?",
                "Nagaland", "Manipur", "Goa", 3,
                Question.DIFFICULTY_HARD, Category.EXPLORE_INDIA);
        insertQuestion(q29);
        Question q30 = new Question("Which of these bodies of water does not border India?",
                "Indian Ocean", "Red Sea", "Arabian Sea", 2,
                Question.DIFFICULTY_HARD, Category.EXPLORE_INDIA);
        insertQuestion(q30);
    }

    public void addQuestion(Question question) {
        db = getWritableDatabase();
        insertQuestion(question);
    }

    public void addQuestions(List<Question> questions) {
        db = getWritableDatabase();

        for (Question question : questions) {
            insertQuestion(question);
        }
    }

    private void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }

        c.close();
        return categoryList;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};

        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}