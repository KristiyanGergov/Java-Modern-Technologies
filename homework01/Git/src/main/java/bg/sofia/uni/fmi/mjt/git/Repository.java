package bg.sofia.uni.fmi.mjt.git;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Repository {
    private ArrayList<String> files;
    private int changedFiles;
    private String currentBranch;
    private String currentCommitHash;
    private Map<String, ArrayList<Commit>> repository;

    public Map<String, ArrayList<Commit>> getRepository() {
        return repository;
    }

    public Repository() {
        this.files = new ArrayList<>();
        this.changedFiles = 0;
        this.currentBranch = "master";
        this.repository = new LinkedHashMap<>();
        this.repository.put(currentBranch, new ArrayList<>());
    }

    public String getCurrentBranch() {
        return currentBranch;
    }

    public Result createBranch(String name) {
        Result result = new Result();
        if (repository.containsKey(name)) {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_CREATE_BRANCH, name));
            result.setSuccessful(false);
        } else {
            repository.put(name, getCurrentBranchCommits());
            result.setMessage(String.format(Constants.SUCCESSFUL_CREATE_BRANCH, name));
            result.setSuccessful(true);
        }
        return result;
    }

    public Result checkoutBranch(String name) {
        Result result = new Result();
        if (repository.containsKey(name)) {
            this.currentBranch = name;
            result.setMessage(String.format(Constants.SUCCESSFUL_CHECKOUT_BRANCH, name));
            result.setSuccessful(true);
        } else {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_CHECKOUT_BRANCH, name));
            result.setSuccessful(false);
        }

        return result;
    }

    public Result checkoutCommit(String hash) {
        Result result = new Result();

        if (getCurrentBranchCommits() == null || getCurrentBranchCommits().size() == 0) {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_CHECKOUT_COMMIT, hash));
            result.setSuccessful(false);
            return result;
        }

        List<Commit> commit = getCurrentBranchCommits()
                .stream()
                .filter(t -> t.getHash().equals(hash))
                .collect(toList());
        if (commit.size() == 0) {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_CHECKOUT_COMMIT, hash));
            result.setSuccessful(false);
        } else {
            this.currentCommitHash = hash;
            files = commit.get(0).getFiles();
            result.setMessage(String.format(Constants.SUCCESSFUL_CHECKOUT_COMMIT, hash));
            result.setSuccessful(true);
        }
        return result;
    }

    public Result add(String... files) {
        if (files.length == 0)
            return null;
        Result result = new Result();
        StringBuilder sb = new StringBuilder();
        boolean contained = false;

        for (String file :
                files) {
            sb.append(file).append(", ");
            if (this.files != null && this.files.indexOf(file) != -1) {
                sb.delete(0, sb.length());
                sb.append(file);
                contained = true;
                break;
            }
        }

        if (contained) {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_ADD, sb.toString()));
            result.setSuccessful(false);
        } else {
            result.setSuccessful(true);
            sb.delete(sb.length() - 2, sb.length());
            result.setMessage(String.format(Constants.SUCCESSFUL_ADD, sb.toString()));
            this.files.addAll(Arrays.asList(files));
            this.changedFiles += files.length;
        }

        return result;
    }

    public Result remove(String... files) {
        if (files.length == 0)
            return null;
        Result result = new Result();
        List<Integer> indexes = new LinkedList<>();
        boolean contained = true;
        for (String file :
                files) {
            int index = this.files.indexOf(file);
            if (index != -1) {
                indexes.add(index);
            } else {
                result.setMessage(String.format(Constants.UNSUCCESSFUL_REMOVE, file));
                result.setSuccessful(false);
                contained = false;
                break;
            }
        }
        if (contained) {
            StringBuilder sb = new StringBuilder();
            int indexRemoval = 0;
            for (int index :
                    indexes) {
                index -= indexRemoval++;
                sb.append(this.files.get(index)).append(", ");
                this.files.remove(index);
            }
            sb.delete(sb.length() - 2, sb.length());
            result.setMessage(String.format(Constants.SUCCESSFUL_REMOVE, sb.toString()));
            result.setSuccessful(true);
            this.changedFiles += files.length;
        }
        return result;
    }

    public Result commit(String message) {
        Result result = new Result();

        if (changedFiles > 0) {
            Commit commit = new Commit(message, new ArrayList<>(files));
            this.currentCommitHash = commit.getHash();
            ArrayList<Commit> commits = getCurrentBranchCommits();
            commits.add(commit);
            result.setSuccessful(true);
            result.setMessage(String.format(Constants.SUCCESSFUL_COMMIT, changedFiles));
            this.repository.put(currentBranch, commits);
            this.changedFiles = 0;
        } else {
            result.setMessage(Constants.UNSUCCESSFUL_COMMIT);
            result.setSuccessful(false);
        }
        return result;
    }

    public Result log() {
        Result result = new Result();

        if (getCurrentBranchCommits().size() == 0) {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_LOG, currentBranch));
            result.setSuccessful(false);
        } else {
            StringBuilder sb = new StringBuilder();
            ArrayList<Commit> currentBranchCommits = getCurrentBranchCommits();
            for (int i = currentBranchCommits.size() - 1; i >= 0; i--) {
                Commit commit = currentBranchCommits.get(i);
                sb.append(String.format(Constants.SUCCESSFUL_LOG, commit.getHash(), commit.getDate(), commit.getMessage()));
                if (i != 0) {
                    sb.append("\n\n");
                }
                result.setSuccessful(true);
            }
            result.setMessage(sb.toString());
        }

        return result;

    }

    public Commit getHead() {
        int size = repository.get(currentBranch).size() - 1;
        if (size < 0)
            return null;

        this.currentCommitHash = repository.get(currentBranch).get(size).getHash();
        return repository.get(currentBranch).get(size);
    }

    private ArrayList<Commit> getCurrentBranchCommits() {
        ArrayList<Commit> commits = new ArrayList<>();
        for (Commit commit :
                repository.get(currentBranch)) {
            commits.add(commit);
            if (commit.getHash().equals(currentCommitHash))
                break;
        }
        return commits;
    }
}